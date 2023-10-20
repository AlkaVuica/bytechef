
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

package com.bytechef.hermes.execution.remote.web.rest.service;

import com.bytechef.hermes.component.definition.TriggerDefinition.DynamicWebhookEnableOutput;
import com.bytechef.hermes.execution.WorkflowExecutionId;
import com.bytechef.hermes.execution.service.TriggerStorageService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ivica Cardic
 */
@RestController
@RequestMapping("${openapi.openAPIDefinition.base-path:}/internal")
public class TriggerStorageServiceController {

    private final TriggerStorageService triggerStorageService;

    @SuppressFBWarnings("EI")
    public TriggerStorageServiceController(TriggerStorageService triggerStorageService) {
        this.triggerStorageService = triggerStorageService;
    }

    @RequestMapping(
        method = RequestMethod.GET,
        value = "/trigger-storage-service/fetch-value/{workflowExecutionId}",
        consumes = {
            "application/json"
        })
    public ResponseEntity<Object> fetchValue(@PathVariable String workflowExecutionId) {
        return triggerStorageService.fetchValue(WorkflowExecutionId.parse(workflowExecutionId))
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.noContent()
                .build());
    }

    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/trigger-storage-service/save/{workflowExecutionId}",
        consumes = {
            "application/json"
        })
    public ResponseEntity<Void> save(@PathVariable String workflowExecutionId, DynamicWebhookEnableOutput value) {
        triggerStorageService.save(WorkflowExecutionId.parse(workflowExecutionId), value);

        return ResponseEntity.noContent()
            .build();
    }
}