
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

package com.bytechef.hermes.definition.registry.remote.web.rest.facade;

import com.bytechef.hermes.definition.registry.dto.OptionDTO;
import com.bytechef.hermes.definition.registry.dto.ValuePropertyDTO;
import com.bytechef.hermes.definition.registry.facade.TriggerDefinitionFacade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
@RestController
@RequestMapping("${openapi.openAPIDefinition.base-path:}/internal")
@ConditionalOnProperty(prefix = "spring", name = "application.name", havingValue = "worker-service-app")
public class TriggerDefinitionFacadeController {

    private final TriggerDefinitionFacade triggerDefinitionFacade;

    public TriggerDefinitionFacadeController(TriggerDefinitionFacade triggerDefinitionFacade) {
        this.triggerDefinitionFacade = triggerDefinitionFacade;
    }

    @RequestMapping(
        method = RequestMethod.POST,
        value = "/trigger-definition-service/execute-editor-description",
        consumes = {
            "application/json"
        })
    public ResponseEntity<String> executeEditorDescription(
        @Valid @RequestBody EditorDescriptionRequest editorDescriptionRequest) {

        return ResponseEntity.ok(
            triggerDefinitionFacade.executeEditorDescription(
                editorDescriptionRequest.componentName, editorDescriptionRequest.componentVersion,
                editorDescriptionRequest.triggerName, editorDescriptionRequest.triggerParameters,
                editorDescriptionRequest.connectionId));
    }

    @RequestMapping(
        method = RequestMethod.POST,
        value = "/trigger-definition-service/execute-options",
        consumes = {
            "application/json"
        },
        produces = {
            "application/json"
        })
    public List<OptionDTO> executeOptions(@Valid @RequestBody OptionsRequest optionsRequest) {
        return triggerDefinitionFacade.executeOptions(
            optionsRequest.componentName, optionsRequest.componentVersion, optionsRequest.triggerName,
            optionsRequest.propertyName, optionsRequest.triggerParameters, optionsRequest.connectionId,
            optionsRequest.searchText);
    }

    @RequestMapping(
        method = RequestMethod.POST,
        value = "/trigger-definition-service/execute-properties",
        consumes = {
            "application/json"
        },
        produces = {
            "application/json"
        })
    public List<? extends ValuePropertyDTO<?>>
        executeProperties(@Valid @RequestBody PropertiesRequest propertiesRequest) {
        return triggerDefinitionFacade.executeDynamicProperties(
            propertiesRequest.componentName, propertiesRequest.componentVersion, propertiesRequest.triggerName,
            propertiesRequest.propertyName, propertiesRequest.triggerParameters, propertiesRequest.connectionId);
    }

    @RequestMapping(
        method = RequestMethod.POST,
        value = "/trigger-definition-service/execute-output-schema",
        consumes = {
            "application/json"
        },
        produces = {
            "application/json"
        })
    public List<? extends ValuePropertyDTO<?>> executeOutputSchema(
        @Valid @RequestBody TriggerDefinitionFacadeController.OutputSchemaRequest outputSchemaRequest) {

        return triggerDefinitionFacade.executeOutputSchema(
            outputSchemaRequest.componentName, outputSchemaRequest.componentVersion, outputSchemaRequest.triggerName,
            outputSchemaRequest.triggerParameters, outputSchemaRequest.connectionId);
    }

    @RequestMapping(
        method = RequestMethod.POST,
        value = "/trigger-definition-service/execute-sample-output",
        consumes = {
            "application/json"
        },
        produces = {
            "application/json"
        })
    public Object executeSampleOutput(@Valid @RequestBody SampleOutputRequest sampleOutputRequest) {
        return triggerDefinitionFacade.executeSampleOutput(
            sampleOutputRequest.componentName, sampleOutputRequest.componentVersion, sampleOutputRequest.triggerName,
            sampleOutputRequest.triggerParameters, sampleOutputRequest.connectionId);
    }

    private record EditorDescriptionRequest(
        @NotNull String componentName, String triggerName, int componentVersion, Map<String, Object> triggerParameters,
        long connectionId) {
    }

    private record OptionsRequest(
        @NotNull String componentName, int componentVersion, @NotNull String triggerName, @NotNull String propertyName,
        Map<String, Object> triggerParameters, long connectionId, String searchText) {
    }

    private record OutputSchemaRequest(
        @NotNull String componentName, int componentVersion, @NotNull String triggerName,
        Map<String, Object> triggerParameters, long connectionId) {
    }

    private record PropertiesRequest(
        @NotNull String componentName, int componentVersion, @NotNull String triggerName, @NotNull String propertyName,
        Map<String, Object> triggerParameters, long connectionId) {
    }

    private record SampleOutputRequest(
        @NotNull String componentName, int componentVersion, @NotNull String triggerName,
        Map<String, Object> triggerParameters, long connectionId) {
    }
}