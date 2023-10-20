
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

package com.bytechef.hermes.definition.registry.remote.client.service;

import com.bytechef.hermes.definition.registry.dto.OptionDTO;
import com.bytechef.hermes.definition.registry.dto.TriggerDefinitionDTO;
import com.bytechef.hermes.definition.registry.dto.ValuePropertyDTO;
import com.bytechef.hermes.definition.registry.remote.client.AbstractWorkerClient;
import com.bytechef.hermes.definition.registry.service.TriggerDefinitionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;
import java.util.Map;

import static com.bytechef.hermes.component.definition.TriggerDefinition.DynamicWebhookEnableOutput;

/**
 * @author Ivica Cardic
 */
public class TriggerDefinitionServiceClient extends AbstractWorkerClient
    implements TriggerDefinitionService {

    public TriggerDefinitionServiceClient(DiscoveryClient discoveryClient, ObjectMapper objectMapper) {
        super(discoveryClient, objectMapper);
    }

    @Override
    public void executeDynamicWebhookDisable(
        String componentName, int componentVersion, String triggerName, Map<String, ?> connectionParameters,
        String authorizationName, Map<String, ?> triggerParameters, String workflowExecutionId,
        DynamicWebhookEnableOutput output) {

        WORKER_WEB_CLIENT
            .post()
            .uri(uriBuilder -> toUri(
                uriBuilder, componentName, "/trigger-definition-service/execute-dynamic-webhook-disable"))
            .bodyValue(
                new DynamicWebhookDisableRequest(
                    authorizationName, componentName, componentVersion, connectionParameters, output, triggerName,
                    triggerParameters, workflowExecutionId))
            .retrieve()
            .toBodilessEntity()
            .block();
    }

    @Override
    public DynamicWebhookEnableOutput executeDynamicWebhookEnable(
        String componentName, int componentVersion, String triggerName, Map<String, ?> connectionParameters,
        String authorizationName, Map<String, ?> triggerParameters, String webhookUrl,
        String workflowExecutionId) {

        return WORKER_WEB_CLIENT
            .post()
            .uri(uriBuilder -> toUri(
                uriBuilder, componentName, "/trigger-definition-service/execute-dynamic-webhook-enable"))
            .bodyValue(
                new DynamicWebhookEnableRequest(
                    authorizationName, componentName, componentVersion, connectionParameters, triggerName,
                    triggerParameters, webhookUrl, workflowExecutionId))
            .retrieve()
            .bodyToMono(DynamicWebhookEnableOutput.class)
            .block();
    }

    @Override
    public DynamicWebhookEnableOutput executeDynamicWebhookRefresh(
        String componentName, int componentVersion, String triggerName, DynamicWebhookEnableOutput output) {

        return WORKER_WEB_CLIENT
            .post()
            .uri(uriBuilder -> toUri(
                uriBuilder, componentName, "/trigger-definition-service/execute-dynamic-webhook-refresh"))
            .bodyValue(new DynamicWebhookRefresh(componentName, componentVersion, output, triggerName))
            .retrieve()
            .bodyToMono(DynamicWebhookEnableOutput.class)
            .block();
    }

    @Override
    public String executeEditorDescription(
        String componentName, int componentVersion, String triggerName, Map<String, ?> triggerParameters,
        String authorizationName, Map<String, ?> connectionParameters) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void executeListenerDisable(
        String componentName, int componentVersion, String triggerName, Map<String, ?> connectionParameters,
        String authorizationName, Map<String, ?> triggerParameters, String workflowExecutionId) {

        WORKER_WEB_CLIENT
            .post()
            .uri(uriBuilder -> toUri(uriBuilder, componentName, "/trigger-definition-service/execute-listener-disable"))
            .bodyValue(
                new ListenerDisableRequest(
                    authorizationName, componentName, componentVersion, connectionParameters, triggerName,
                    triggerParameters, workflowExecutionId))
            .retrieve()
            .toBodilessEntity()
            .block();
    }

    @Override
    public void executeListenerEnable(
        String componentName, int componentVersion, String triggerName, Map<String, ?> connectionParameters,
        String authorizationName, Map<String, ?> triggerParameters,
        String workflowExecutionId) {

        WORKER_WEB_CLIENT
            .post()
            .uri(uriBuilder -> toUri(uriBuilder, componentName, "/trigger-definition-service/execute-listener-enable"))
            .bodyValue(
                new ListenerEnableRequest(
                    authorizationName, componentName, componentVersion, connectionParameters, triggerName,
                    triggerParameters, workflowExecutionId))
            .retrieve()
            .toBodilessEntity()
            .block();
    }

    @Override
    public List<OptionDTO> executeOptions(
        String componentName, int componentVersion, String triggerName, String propertyName,
        Map<String, ?> triggerParameters, String authorizationName, Map<String, ?> connectionParameters,
        String searchText) {

        throw new UnsupportedOperationException();
    }

    @Override
    public List<? extends ValuePropertyDTO<?>> executeOutputSchema(
        String componentName, int componentVersion, String triggerName, Map<String, ?> triggerParameters,
        String authorizationName, Map<String, ?> connectionParameters) {

        throw new UnsupportedOperationException();
    }

    @Override
    public List<? extends ValuePropertyDTO<?>> executeDynamicProperties(
        String componentName, int componentVersion, String triggerName, String propertyName,
        Map<String, ?> triggerParameters, String authorizationName, Map<String, ?> connectionParameters) {

        throw new UnsupportedOperationException();
    }

    @Override
    public Object executeSampleOutput(
        String componentName, int componentVersion, String triggerName, Map<String, ?> triggerParameters,
        String authorizationName, Map<String, ?> connectionParameters) {

        throw new UnsupportedOperationException();
    }

    @Override
    public TriggerDefinitionDTO getTriggerDefinition(String componentName, int componentVersion, String triggerName) {
        return WORKER_WEB_CLIENT
            .get()
            .uri(uriBuilder -> toUri(
                uriBuilder, componentName,
                "/trigger-definition-service/get-trigger-definition/{componentName}/{componentVersion}/{triggerName}",
                componentName, componentVersion, triggerName))
            .retrieve()
            .bodyToMono(TriggerDefinitionDTO.class)
            .block();
    }

    @Override
    public List<TriggerDefinitionDTO> getTriggerDefinitions(String componentName, int componentVersion) {
        return WORKER_WEB_CLIENT
            .get()
            .uri(uriBuilder -> toUri(
                uriBuilder, componentName,
                "/trigger-definition-service/get-trigger-definitions/{componentName}/{componentVersion}", componentName,
                componentVersion))
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<TriggerDefinitionDTO>>() {})
            .block();
    }

    private record DynamicWebhookDisableRequest(
        String authorizationName, String componentName, int componentVersion,
        Map<String, ?> connectionParameters, DynamicWebhookEnableOutput output, String triggerName,
        Map<String, ?> triggerParameters, String workflowExecutionId) {
    }

    private record DynamicWebhookEnableRequest(
        String authorizationName, String componentName, int componentVersion, Map<String, ?> connectionParameters,
        String triggerName, Map<String, ?> triggerParameters, String webhookUrl, String workflowExecutionId) {
    }

    private record DynamicWebhookRefresh(
        String componentName, int componentVersion, DynamicWebhookEnableOutput output, String triggerName) {
    }

    private record ListenerDisableRequest(
        String authorizationName, String componentName, int componentVersion, Map<String, ?> connectionParameters,
        String triggerName, Map<String, ?> triggerParameters, String workflowExecutionId) {
    }

    private record ListenerEnableRequest(
        String authorizationName, String componentName, int componentVersion, Map<String, ?> connectionParameters,
        String triggerName, Map<String, ?> triggerParameters, String workflowExecutionId) {
    }
}