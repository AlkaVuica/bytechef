
/*
 * Copyright 2021 <your company/name>.
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

package com.bytechef.hermes.connection.remote.client.service;

import com.bytechef.commons.webclient.LoadBalancedWebClient;
import com.bytechef.hermes.connection.domain.Connection;
import com.bytechef.hermes.connection.service.RemoteConnectionService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author Ivica Cardic
 */
@Component
public class RemoteConnectionServiceClient implements RemoteConnectionService {

    private final LoadBalancedWebClient loadBalancedWebClient;

    @SuppressFBWarnings("EI")
    public RemoteConnectionServiceClient(LoadBalancedWebClient loadBalancedWebClient) {
        this.loadBalancedWebClient = loadBalancedWebClient;
    }

    @Override
    public Optional<Connection> fetchConnection(long id) {
        return Optional.ofNullable(
            loadBalancedWebClient.get(
                uriBuilder -> uriBuilder
                    .host("connection-app")
                    .path("/remote/connection-service/fetch-connection/{id}")
                    .build(id),
                Connection.class));
    }

    @Override
    public Connection getConnection(long id) {
        return fetchConnection(id).orElseThrow();
    }

    @Override
    public List<Connection> getConnections() {
        return loadBalancedWebClient.get(
            uriBuilder -> uriBuilder
                .host("connection-app")
                .path("/remote/connection-service/get-connections")
                .build(),
            new ParameterizedTypeReference<>() {});
    }
}