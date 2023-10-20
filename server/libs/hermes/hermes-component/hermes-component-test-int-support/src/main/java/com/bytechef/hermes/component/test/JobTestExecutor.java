
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

package com.bytechef.hermes.component.test;

import com.bytechef.atlas.execution.domain.Job;
import com.bytechef.atlas.execution.dto.JobParameters;
import com.bytechef.commons.util.MapUtils;
import com.bytechef.atlas.execution.service.RemoteContextService;
import com.bytechef.atlas.execution.service.RemoteJobService;
import com.bytechef.atlas.execution.service.RemoteTaskExecutionService;
import com.bytechef.atlas.configuration.service.RemoteWorkflowService;
import com.bytechef.atlas.sync.executor.JobSyncExecutor;
import com.bytechef.atlas.worker.task.handler.TaskHandler;
import com.bytechef.file.storage.base64.service.Base64FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import com.bytechef.atlas.file.storage.facade.WorkflowFileStorageFacadeImpl;

import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class JobTestExecutor {

    private final RemoteContextService contextService;
    private final RemoteJobService jobService;
    private final ObjectMapper objectMapper;
    private final RemoteTaskExecutionService taskExecutionService;
    private final Map<String, TaskHandler<?>> taskHandlerMap;
    private final RemoteWorkflowService workflowService;

    @SuppressFBWarnings("EI")
    public JobTestExecutor(
        RemoteContextService contextService, RemoteJobService jobService, ObjectMapper objectMapper,
        RemoteTaskExecutionService taskExecutionService, Map<String, TaskHandler<?>> taskHandlerMap,
        RemoteWorkflowService workflowService) {

        this.contextService = contextService;
        this.jobService = jobService;
        this.objectMapper = objectMapper;
        this.taskExecutionService = taskExecutionService;
        this.taskHandlerMap = taskHandlerMap;
        this.workflowService = workflowService;
    }

    public Job execute(String workflowId, Map<String, Object> inputs) {
        return execute(workflowId, inputs, Map.of());
    }

    public Job execute(String workflowId, Map<String, Object> inputs, Map<String, TaskHandler<?>> taskHandlerMap) {
        JobSyncExecutor jobSyncExecutor = new JobSyncExecutor(
            contextService, jobService, objectMapper, taskExecutionService,
            MapUtils.concat(this.taskHandlerMap, taskHandlerMap)::get,
            new WorkflowFileStorageFacadeImpl(new Base64FileStorageService(), new ObjectMapper()), workflowService);

        return jobSyncExecutor.execute(new JobParameters(workflowId, inputs));
    }
}