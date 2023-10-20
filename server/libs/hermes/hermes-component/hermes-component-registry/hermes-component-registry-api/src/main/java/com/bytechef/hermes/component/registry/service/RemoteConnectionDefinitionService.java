
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

package com.bytechef.hermes.component.registry.service;

import com.bytechef.hermes.component.definition.Authorization.AuthorizationType;
import com.bytechef.hermes.component.registry.domain.ConnectionDefinition;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author Ivica Cardic
 */
public interface RemoteConnectionDefinitionService {

    AuthorizationType getAuthorizationType(
        @NonNull String componentName, int connectionVersion, @NonNull String authorizationName);

    ConnectionDefinition getConnectionDefinition(@NonNull String componentName, int componentVersion);

    List<ConnectionDefinition> getConnectionDefinitions();

    List<ConnectionDefinition> getConnectionDefinitions(
        @NonNull String componentName, @NonNull Integer componentVersion);
}