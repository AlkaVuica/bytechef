
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

package com.bytechef.platform.config;

import com.bytechef.hermes.definition.registry.service.TriggerDefinitionService;
import com.bytechef.hermes.scheduler.TriggerScheduler;
import com.bytechef.hermes.execution.service.TriggerStateService;
import com.bytechef.hermes.execution.facade.TriggerLifecycleFacade;
import com.bytechef.hermes.execution.facade.TriggerLifecycleFacadeImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ivica Cardic
 */
@Configuration
public class TriggerConfiguration {

    @Bean
    TriggerLifecycleFacade triggerLifecycleManager(
        TriggerScheduler triggerScheduler, TriggerDefinitionService triggerDefinitionService,
        TriggerStateService triggerStateService, @Value("bytechef.webhookUrl") String webhookUrl) {

        return new TriggerLifecycleFacadeImpl(
            triggerScheduler, triggerDefinitionService, triggerStateService, webhookUrl);
    }
}