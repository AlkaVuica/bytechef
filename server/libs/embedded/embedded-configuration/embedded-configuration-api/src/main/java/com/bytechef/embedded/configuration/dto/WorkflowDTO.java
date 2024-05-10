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

package com.bytechef.embedded.configuration.dto;

import com.bytechef.atlas.configuration.domain.Workflow;
import com.bytechef.platform.configuration.dto.WorkflowTaskDTO;
import com.bytechef.platform.configuration.dto.WorkflowTriggerDTO;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Ivica Cardic
 */
@SuppressFBWarnings("EI")
public record WorkflowDTO(
    String createdBy, LocalDateTime createdDate, String definition, String description, Workflow.Format format,
    String id, List<Workflow.Input> inputs, long integrationWorkflowId, String label, String lastModifiedBy,
    LocalDateTime lastModifiedDate, List<Workflow.Output> outputs, Workflow.SourceType sourceType, int maxRetries,
    List<WorkflowTaskDTO> tasks, List<WorkflowTriggerDTO> triggers, int version, Workflow workflow) {

    public WorkflowDTO(Workflow workflow, long integrationWorkflowId) {
        this(
            workflow.getCreatedBy(), workflow.getCreatedDate(), workflow.getDefinition(), workflow.getDescription(),
            workflow.getFormat(), workflow.getId(), workflow.getInputs(), integrationWorkflowId, workflow.getLabel(),
            workflow.getLastModifiedBy(), workflow.getLastModifiedDate(), workflow.getOutputs(),
            workflow.getSourceType(), workflow.getMaxRetries(),
            workflow.getAllTasks()
                .stream()
                .map(workflowTask -> new WorkflowTaskDTO(workflowTask, List.of(), null))
                .toList(),
            List.of(), workflow.getVersion(), workflow);
    }

    public WorkflowDTO(
        com.bytechef.platform.configuration.dto.WorkflowDTO workflow, long integrationWorkflowId) {

        this(
            workflow.createdBy(), workflow.createdDate(), workflow.definition(), workflow.description(),
            workflow.format(), workflow.id(), workflow.inputs(), integrationWorkflowId, workflow.label(),
            workflow.lastModifiedBy(), workflow.lastModifiedDate(), workflow.outputs(),
            workflow.sourceType(), workflow.maxRetries(), workflow.tasks(), workflow.triggers(),
            workflow.version(), workflow.workflow());
    }
}