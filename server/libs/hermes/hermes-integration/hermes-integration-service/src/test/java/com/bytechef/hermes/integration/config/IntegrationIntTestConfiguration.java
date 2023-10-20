
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

package com.bytechef.hermes.integration.config;

import com.bytechef.atlas.repository.WorkflowRepository;
import com.bytechef.atlas.repository.config.WorkflowRepositoryConfiguration;
import com.bytechef.atlas.service.WorkflowService;
import com.bytechef.atlas.service.impl.WorkflowServiceImpl;
import java.util.Optional;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

/**
 * @author Ivica Cardic
 */
@ComponentScan(
    basePackages = {
        "com.bytechef.atlas.repository.jdbc", "com.bytechef.hermes.integration"
    })
@EnableAutoConfiguration
@Import({
    WorkflowRepositoryConfiguration.class
})
@SpringBootConfiguration
public class IntegrationIntTestConfiguration {

    @Bean
    public WorkflowService workflowService(WorkflowRepository workflowRepository) {
        return new WorkflowServiceImpl(workflowRepository);
    }

    @EnableCaching
    @TestConfiguration
    public static class CacheConfiguration {
    }

    @EnableJdbcAuditing
    @EnableJdbcRepositories(
        basePackages = {
            "com.bytechef.atlas.repository.jdbc", "com.bytechef.hermes.integration.repository"
        })
    @TestConfiguration
    public static class JdbcRepositoriesConfiguration {

        @Bean
        AuditorAware<String> auditorProvider() {
            return () -> Optional.of("system");
        }

        @Bean
        public DateTimeProvider auditingDateTimeProvider() {
            return CurrentDateTimeProvider.INSTANCE;
        }
    }
}
