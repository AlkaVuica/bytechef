/*
 * Copyright 2023-present ByteChef Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bytechef.platform.component.registry.definition;

import com.bytechef.commons.util.MimeTypeUtils;
import com.bytechef.commons.util.OptionalUtils;
import com.bytechef.component.definition.Authorization.ApplyResponse;
import com.bytechef.component.definition.Context;
import com.bytechef.component.definition.Context.Http;
import com.bytechef.component.definition.Context.Http.Body;
import com.bytechef.component.definition.Context.Http.Configuration;
import com.bytechef.component.definition.Context.Http.RequestMethod;
import com.bytechef.component.definition.Context.Http.Response;
import com.bytechef.component.definition.Context.Http.ResponseType;
import com.bytechef.component.definition.FileEntry;
import com.bytechef.file.storage.service.FileStorageService;
import com.bytechef.platform.component.registry.domain.ComponentConnection;
import com.bytechef.platform.component.registry.service.ConnectionDefinitionService;
import com.bytechef.platform.exception.ProviderException;
import com.bytechef.platform.workflow.execution.constants.FileEntryConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mizosoft.methanol.FormBodyPublisher;
import com.github.mizosoft.methanol.MediaType;
import com.github.mizosoft.methanol.Methanol;
import com.github.mizosoft.methanol.MoreBodyPublishers;
import com.github.mizosoft.methanol.MultipartBodyPublisher;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author Ivica Cardic
 */
@Component
public class HttpClientExecutor {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientExecutor.class);

    private final ConnectionDefinitionService connectionDefinitionService;
    private final FileStorageService fileStorageService;
    private final ObjectMapper objectMapper;

    @SuppressFBWarnings("EI")
    public HttpClientExecutor(
        ConnectionDefinitionService connectionDefinitionService, FileStorageService fileStorageService,
        ObjectMapper objectMapper) {

        this.connectionDefinitionService = connectionDefinitionService;
        this.fileStorageService = fileStorageService;
        this.objectMapper = objectMapper;
    }

    public Response execute(
        String urlString, Map<String, List<String>> headers, Map<String, List<String>> queryParameters, Body body,
        Configuration configuration, RequestMethod requestMethod, String componentName,
        ComponentConnection componentConnection,
        Context context) throws Exception {

        HttpResponse<?> httpResponse;

        try (HttpClient httpClient = createHttpClient(
            headers, queryParameters, configuration, componentName, componentConnection, context)) {

            HttpRequest httpRequest = createHTTPRequest(
                urlString, requestMethod, headers, queryParameters, body, componentName, componentConnection, context);

            if (logger.isDebugEnabled()) {
                logger.debug(
                    "uri: {}, requestMethod: {}, headers: {}, queryParameters: {}, responseType: {}",
                    httpRequest.uri(), requestMethod, headers, queryParameters, requestMethod);
            }

            httpResponse = httpClient.send(httpRequest, createBodyHandler(configuration));
        }

        return handleResponse(httpResponse, configuration);
    }

    HttpResponse.BodyHandler<?> createBodyHandler(Configuration configuration) {
        HttpResponse.BodyHandler<?> bodyHandler;
        ResponseType responseType = configuration.getResponseType();

        if (responseType == null) {
            bodyHandler = HttpResponse.BodyHandlers.discarding();
        } else {
            if (responseType == Http.ResponseType.BINARY) {
                bodyHandler = HttpResponse.BodyHandlers.ofInputStream();
            } else {
                bodyHandler = HttpResponse.BodyHandlers.ofString();
            }
        }

        return bodyHandler;
    }

    BodyPublisher createBodyPublisher(Body body) {
        BodyPublisher bodyPublisher;

        if (body == null) {
            bodyPublisher = BodyPublishers.noBody();
        } else {
            if (body.getContentType() == Http.BodyContentType.BINARY
                && body.getContent() instanceof FileEntry fileEntry) {
                bodyPublisher = getBinaryBodyPublisher(body, fileEntry);
            } else if (body.getContentType() == Http.BodyContentType.FORM_DATA) {
                bodyPublisher = getFormDataBodyPublisher(body);
            } else if (body.getContentType() == Http.BodyContentType.FORM_URL_ENCODED) {
                bodyPublisher = getFormUrlEncodedBodyPublisher(body);
            } else if (body.getContentType() == Http.BodyContentType.JSON) {
                bodyPublisher = getJsonBodyPublisher(body);
            } else if (body.getContentType() == Http.BodyContentType.XML) {
                bodyPublisher = getXmlBodyPublisher(body);
            } else {
                bodyPublisher = getStringBodyPublisher(body);
            }
        }

        return bodyPublisher;
    }

    HttpClient createHttpClient(
        Map<String, List<String>> headers, Map<String, List<String>> queryParameters, Configuration configuration,
        String componentName, ComponentConnection componentConnection, Context context) {

        Methanol.Builder builder = Methanol.newBuilder()
            .version(HttpClient.Version.HTTP_1_1);

        if (configuration.isAllowUnauthorizedCerts()) {
            try {
                SSLContext sslContext = SSLContext.getInstance("TLS");

                sslContext.init(null, new TrustManager[] {
                    new UnauthorizedCertsX509ExtendedTrustManager()
                }, null);

                builder.sslContext(sslContext);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if (!configuration.isDisableAuthorization()) {
            applyAuthorization(headers, queryParameters, componentName, componentConnection, context);

            if (Objects.equals("oauth2_authorization_code", componentConnection.getAuthorizationName())) {
                builder.interceptor(getInterceptor());
            }
        }

        if (configuration.isFollowRedirect()) {
            builder.followRedirects(HttpClient.Redirect.NORMAL);
        }

        if (configuration.isFollowAllRedirects()) {
            builder.followRedirects(HttpClient.Redirect.ALWAYS);
        }

        String proxy = configuration.getProxy();

        if (StringUtils.isNoneEmpty(proxy)) {
            String[] hostPortArray = proxy.split(":");

            builder.proxy(
                ProxySelector.of(new InetSocketAddress(hostPortArray[0], Integer.parseInt(hostPortArray[1]))));
        }

        if (configuration.getTimeout() == null) {
            builder.connectTimeout(Duration.ofMillis(4000));
        } else {
            builder.connectTimeout(configuration.getTimeout());
        }

        return builder.build();
    }

    private Methanol.Interceptor getInterceptor() {
        return new Methanol.Interceptor() {
            @Override
            public <T> HttpResponse<T> intercept(HttpRequest httpRequest, Chain<T> chain)
                throws IOException, InterruptedException {
                logger.trace("Intercepting OAuth Authorized request to analyze response");

                HttpResponse<T> httpResponse = chain.forward(httpRequest);

                if ((httpResponse.statusCode() > 199) && (httpResponse.statusCode() < 300)) {
                    return httpResponse;
                }

                logger.debug("Rise exception");

                throw ProviderException.getByHttpResponseCode(httpResponse.statusCode(), "Token expired");
            }

            @Override
            public <T> CompletableFuture<HttpResponse<T>> interceptAsync(HttpRequest httpRequest, Chain<T> chain) {
                logger.trace("Intercepting ASYNC OAuth Authorized request to analyze response");

                return chain.forwardAsync(httpRequest);
            }
        };
    }

    HttpRequest createHTTPRequest(
        String urlString, RequestMethod requestMethod, Map<String, List<String>> headers,
        Map<String, List<String>> queryParameters, Body body, String componentName,
        ComponentConnection componentConnection,
        Context context) {

        HttpRequest.Builder httpRequestBuilder = HttpRequest
            .newBuilder()
            .method(requestMethod.name(), createBodyPublisher(body));

        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            for (String value : entry.getValue()) {
                httpRequestBuilder.header(entry.getKey(), value);
            }
        }

        httpRequestBuilder.uri(
            createURI(
                getConnectionUrl(urlString, componentName, componentConnection, context),
                queryParameters == null ? Collections.emptyMap() : queryParameters));

        return httpRequestBuilder.build();
    }

    Response handleResponse(HttpResponse<?> httpResponse, Configuration configuration) {
        Response response;
        HttpHeaders httpHeaders = httpResponse.headers();

        Map<String, List<String>> headers = httpHeaders.map();

        if (configuration.getResponseType() == null) {
            response = new ResponseImpl(headers, null, httpResponse.statusCode());
        } else {
            Object httpResponseBody = httpResponse.body();
            ResponseType responseType = configuration.getResponseType();

            Object body;

            if (!isEmpty(httpResponseBody) && responseType == Http.ResponseType.BINARY) {
                body = storeBinaryResponseBody(configuration, headers, (InputStream) httpResponseBody);
            } else if (responseType == Http.ResponseType.JSON) {
                body = isEmpty(httpResponseBody) ? null
                    : com.bytechef.commons.util.JsonUtils.read(httpResponseBody.toString());
            } else if (responseType == Http.ResponseType.TEXT) {
                body = isEmpty(httpResponseBody) ? null : httpResponseBody.toString();
            } else {
                body = isEmpty(httpResponseBody) ? null
                    : com.bytechef.commons.util.XmlUtils.read(httpResponseBody.toString());
            }

            response = new ResponseImpl(headers, body, httpResponse.statusCode());
        }

        return response;
    }

    private void addFileEntry(MultipartBodyPublisher.Builder builder, String name, FileEntry fileEntry) {
        builder.formPart(
            name, fileEntry.getName(),
            MoreBodyPublishers.ofMediaType(
                BodyPublishers.ofInputStream(() -> fileStorageService.getFileStream(
                    FileEntryConstants.FILES_DIR, ((FileEntryImpl) fileEntry).getFileEntry())),
                MediaType.parse(fileEntry.getMimeType())));
    }

    private void applyAuthorization(
        Map<String, List<String>> headers, Map<String, List<String>> queryParameters, String componentName,
        ComponentConnection componentConnection, Context context) {

        if ((componentConnection == null) || Objects.equals(componentConnection.getAuthorizationName(), "none")) {
            return;
        }

        ApplyResponse applyResponse = connectionDefinitionService.executeAuthorizationApply(
            componentName, componentConnection, context);

        if (applyResponse != null) {
            headers.putAll(applyResponse.getHeaders());
            queryParameters.putAll(applyResponse.getQueryParameters());
        }
    }

    private URI createURI(String urlString, @NonNull Map<String, List<String>> queryParameters) {
        URI uri;

        if (queryParameters.isEmpty()) {
            uri = URI.create(urlString);
        } else {
            String parameter = queryParameters
                .entrySet()
                .stream()
                .flatMap(entry -> entry
                    .getValue()
                    .stream()
                    .map(value -> entry.getKey() + "=" + value))
                .collect(Collectors.joining("&"));

            uri = URI.create(urlString + '?' + parameter);
        }

        return uri;
    }

    private String getConnectionUrl(
        String urlString, String componentName, ComponentConnection componentConnection, Context context) {

        if (urlString.contains("://") || (componentConnection == null)) {
            return urlString;
        }

        return OptionalUtils.map(
            connectionDefinitionService.executeBaseUri(componentName, componentConnection, context),
            baseUri -> baseUri + urlString);

    }

    private BodyPublisher getBinaryBodyPublisher(Body body, FileEntry fileEntry) {
        return MoreBodyPublishers.ofMediaType(
            BodyPublishers.ofInputStream(() -> fileStorageService.getFileStream(
                FileEntryConstants.FILES_DIR, ((FileEntryImpl) fileEntry).getFileEntry())),
            MediaType.parse(body.getMimeType() == null ? fileEntry.getMimeType() : body.getMimeType()));
    }

    private BodyPublisher getFormDataBodyPublisher(Body body) {
        Map<?, ?> bodyParameters = (Map<?, ?>) body.getContent();

        MultipartBodyPublisher.Builder builder = MultipartBodyPublisher.newBuilder();

        for (Map.Entry<?, ?> parameter : bodyParameters.entrySet()) {
            if (parameter.getValue() instanceof FileEntry fileEntry) {
                addFileEntry(builder, (String) parameter.getKey(), fileEntry);
            } else {
                builder.textPart((String) parameter.getKey(), parameter.getValue());
            }
        }

        return builder.build();
    }

    private BodyPublisher getFormUrlEncodedBodyPublisher(Body body) {
        Map<?, ?> bodyParameters = (Map<?, ?>) body.getContent();

        FormBodyPublisher.Builder builder = FormBodyPublisher.newBuilder();

        for (Map.Entry<?, ?> parameter : bodyParameters.entrySet()) {
            Object value = Validate.notNull(parameter.getValue(), "expected value for " + parameter.getKey());

            builder.query((String) parameter.getKey(), value.toString());
        }

        return builder.build();
    }

    private BodyPublisher getStringBodyPublisher(Body body) {
        Object content = body.getContent();

        return MoreBodyPublishers.ofMediaType(
            BodyPublishers.ofString(content.toString()),
            MediaType.parse(body.getMimeType()));
    }

    private boolean isEmpty(final Object object) {
        if (object == null) {
            return true;
        }

        if (object instanceof CharSequence) {
            return ((CharSequence) object).isEmpty();
        }

        return false;
    }

    private FileEntry storeBinaryResponseBody(
        Configuration configuration, Map<String, List<String>> headers, InputStream httpResponseBody) {

        String filename = configuration.getFilename();

        if (filename == null || filename.isEmpty()) {
            if (headers.containsKey("Content-Type")) {
                List<String> values = headers.get("Content-Type");

                filename = "file." + MimeTypeUtils.getDefaultExt(values.getFirst());
            } else {
                filename = "file.txt";
            }
        }

        return new FileEntryImpl(
            fileStorageService.storeFileContent(FileEntryConstants.FILES_DIR, filename, httpResponseBody));
    }

    private BodyPublisher getJsonBodyPublisher(Body body) {
        return MoreBodyPublishers.ofMediaType(
            BodyPublishers.ofString(com.bytechef.commons.util.JsonUtils.write(body.getContent())),
            MediaType.APPLICATION_JSON);
    }

    private BodyPublisher getXmlBodyPublisher(Body body) {
        return MoreBodyPublishers.ofMediaType(
            BodyPublishers.ofString(com.bytechef.commons.util.XmlUtils.write(body.getContent())),
            MediaType.APPLICATION_XML);
    }

    private class ResponseImpl implements Response {

        private final Map<String, List<String>> headers;
        private final Object body;
        private final int statusCode;

        private ResponseImpl(Map<String, List<String>> headers, Object body, int statusCode) {
            this.headers = headers;
            this.body = body;
            this.statusCode = statusCode;
        }

        @Override
        public Map<String, List<String>> getHeaders() {
            return headers;
        }

        @Override
        public Object getBody() {
            return objectMapper.convertValue(body, new TypeReference<>() {});
        }

        @Override
        public <T> T getBody(Class<T> valueType) {
            return objectMapper.convertValue(body, valueType);
        }

        @Override
        public <T> T getBody(Context.TypeReference<T> valueTypeRef) {
            return objectMapper.convertValue(body, new TypeReference<>() {

                @Override
                public Type getType() {
                    return valueTypeRef.getType();
                }
            });
        }

        @Override
        public String getFirstHeader(String name) {
            List<String> values = headers.get(name);

            return values.getFirst();
        }

        @Override
        public List<String> getHeader(String name) {
            return headers.get(name);
        }

        @Override
        public int getStatusCode() {
            return statusCode;
        }
    }

    private static class UnauthorizedCertsX509ExtendedTrustManager extends X509ExtendedTrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(final X509Certificate[] x509Certificates, final String authType) {
        }

        public void checkServerTrusted(final X509Certificate[] x509Certificates, final String authType) {
        }

        public void checkClientTrusted(
            final X509Certificate[] x509Certificates, final String certificates, final Socket socket) {
        }

        public void checkServerTrusted(
            final X509Certificate[] x509Certificates, final String authType, final Socket socket) {
        }

        public void checkClientTrusted(
            final X509Certificate[] x509Certificates, final String authType, final SSLEngine engine) {
        }

        public void checkServerTrusted(
            final X509Certificate[] x509Certificates, final String authType, final SSLEngine engine) {
        }
    }
}
