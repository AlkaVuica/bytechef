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

package com.bytechef.automation.configuration.service;

import com.bytechef.automation.configuration.domain.Project;
import com.bytechef.automation.configuration.domain.ProjectVersion;
import com.bytechef.automation.configuration.domain.ProjectVersion.Status;
import com.bytechef.automation.configuration.repository.ProjectRepository;
import com.bytechef.commons.util.CollectionUtils;
import com.bytechef.commons.util.OptionalUtils;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.Validate;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ivica Cardic
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public int addVersion(long id) {
        Project project = getProject(id);

        int newVersion = project.addVersion();

        projectRepository.save(project);

        return newVersion;
    }

    @Override
    public long countProjects() {
        return projectRepository.count();
    }

    @Override
    public Project create(Project project) {
        Validate.notNull(project, "'project' must not be null");
        Validate.isTrue(project.getId() == null, "'id' must be null");
        Validate.notNull(project.getName(), "'name' must not be null");

        return projectRepository.save(project);
    }

    @Override
    public void delete(long id) {
        projectRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Project> fetchProject(String name) {
        return projectRepository.findByNameIgnoreCase(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Project> fetchWorkflowProject(String workflowId) {
        return projectRepository.findByWorkflowId(workflowId);
    }

    @Override
    @Transactional(readOnly = true)
    public Project getProjectInstanceProject(long projectInstanceId) {
        return projectRepository.findByProjectInstanceId(projectInstanceId);
    }

    @Override
    @Transactional(readOnly = true)
    public Project getProject(long id) {
        return OptionalUtils.get(projectRepository.findById(id));
    }

    @Override
    public List<ProjectVersion> getProjectVersions(Long id) {
        Project project = getProject(id);

        return project.getProjectVersions()
            .stream()
            .sorted((o1, o2) -> Integer.compare(o2.getVersion(), o1.getVersion()))
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getProjects() {
        return CollectionUtils.toList(projectRepository.findAll(Sort.by("name")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getProjects(List<Long> ids) {
        return CollectionUtils.toList(projectRepository.findAllById(ids));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getProjects(Long workspaceId, Long categoryId, List<Long> ids, Long tagId, Status status) {
        return projectRepository.findAllProjects(
            workspaceId, categoryId, ids, tagId, status == null ? null : status.ordinal());
    }

    @Override
    @Transactional(readOnly = true)
    public Project getWorkflowProject(String workflowId) {
        return OptionalUtils.get(projectRepository.findByWorkflowId(workflowId));
    }

    @Override
    public Project publishProject(long id, String description) {
        Project project = getProject(id);

        project.publish(description);

        return projectRepository.save(project);
    }

    @Override
    public Project update(long id, List<Long> tagIds) {
        Project project = getProject(id);

        project.setTagIds(tagIds);

        return projectRepository.save(project);
    }

    @Override
    public Project update(Project project) {
        Validate.notNull(project, "'project' must not be null");

        Project curProject = getProject(Validate.notNull(project.getId(), "id"));

        curProject.setCategoryId(project.getCategoryId());
        curProject.setDescription(project.getDescription());
        curProject.setName(Validate.notNull(project.getName(), "name"));
        curProject.setTagIds(project.getTagIds());
        curProject.setVersion(project.getVersion());

        return projectRepository.save(curProject);
    }
}
