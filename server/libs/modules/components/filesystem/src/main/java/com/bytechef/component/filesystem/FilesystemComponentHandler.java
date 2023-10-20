
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

package com.bytechef.component.filesystem;

import static com.bytechef.component.filesystem.constant.FilesystemConstants.FILESYSTEM;
import static com.bytechef.hermes.component.definition.ComponentDSL.component;
import static com.bytechef.hermes.component.definition.ComponentDSL.display;

import com.bytechef.component.filesystem.action.FilesystemCreateTempDirAction;
import com.bytechef.component.filesystem.action.FilesystemGetFilePathAction;
import com.bytechef.component.filesystem.action.FilesystemLsAction;
import com.bytechef.component.filesystem.action.FilesystemMkdirAction;
import com.bytechef.component.filesystem.action.FilesystemReadFileAction;
import com.bytechef.component.filesystem.action.FilesystemRmAction;
import com.bytechef.component.filesystem.action.FilesystemWriteFileAction;
import com.bytechef.hermes.component.ComponentHandler;
import com.bytechef.hermes.component.definition.ComponentDefinition;

/**
 * @author Ivica Cardic
 */
public class FilesystemComponentHandler implements ComponentHandler {

    private static final ComponentDefinition COMPONENT_DEFINITION = component(FILESYSTEM)
        .display(display("Local File").description("Reads or writes a binary file from/to disk"))
        .actions(
            FilesystemReadFileAction.READ_FILE_ACTION,
            FilesystemWriteFileAction.WRITE_FILE_ACTION,
            FilesystemCreateTempDirAction.CREATE_TEMP_DIR_ACTION,
            FilesystemGetFilePathAction.GET_FILE_PATH_ACTION,
            FilesystemLsAction.LS_ACTION,
            FilesystemMkdirAction.MKDIR_ACTION,
            FilesystemRmAction.RM_ACTION);

    @Override
    public ComponentDefinition getDefinition() {
        return COMPONENT_DEFINITION;
    }
}
