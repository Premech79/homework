package com.sky.homework.module.project.service;

import com.sky.homework.module.project.entity.Project;
import com.sky.homework.module.project.exception.ProjectKeyExistsException;
import com.sky.homework.module.project.service.command.CreateProjectCommand;
import com.sky.homework.module.project.service.command.UpdateProjectCommand;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

	private final ProjectFinderService projectFinderService = mock(ProjectFinderService.class);
	private final ProjectRepository projectRepository = mock(ProjectRepository.class);

	private final ProjectService projectService = new ProjectService(projectFinderService, projectRepository);

	@Test
	void create_project_shouldThrowException_whenKeyExists() {
		CreateProjectCommand command = new CreateProjectCommand("KEY", "Project name");
		when(projectFinderService.existsByKey(command.key())).thenReturn(true);

		assertThrows(ProjectKeyExistsException.class, () -> projectService.create(command));
	}

	@Test
	void create_project_shouldSaveAndReturnProject_whenValid() {
		CreateProjectCommand command = new CreateProjectCommand("KEY", "Project name");
		when(projectFinderService.existsByKey(command.key())).thenReturn(false);

		Project project = new Project();
		project.setId(UUID.fromString("7b3ef0d1-2122-44d2-9ae5-1c0cdbce4985"));
		project.setKey("New KEY");
		project.setName("New project name");
		when(projectRepository.save(any(Project.class))).thenReturn(project);

		Project createdProject = projectService.create(command);

		assertEquals("New KEY", createdProject.getKey());
		assertEquals("New project name", createdProject.getName());
		verify(projectRepository).save(any(Project.class));
	}

	@Test
	void update_project_shouldThrowException_whenKeyExists() {
		UpdateProjectCommand command = new UpdateProjectCommand(
				UUID.fromString("82a7be0e-6892-41dd-a0cf-8879108f3eb1"),
				"KEY",
				"Project name");
		when(projectFinderService.existsByKey(command.key())).thenReturn(true);

		assertThrows(ProjectKeyExistsException.class, () -> projectService.update(command));
	}

	@Test
	void update_project_shouldSaveAndReturnUpdatedProject_whenValid() {
		UpdateProjectCommand command = new UpdateProjectCommand(
				UUID.fromString("82a7be0e-6892-41dd-a0cf-8879108f3eb1"),
				"KEY",
				"Project name");
		when(projectFinderService.existsByKey(command.key())).thenReturn(false);

		Project project = new Project();
		project.setId(UUID.fromString("7b3ef0d1-2122-44d2-9ae5-1c0cdbce4985"));
		project.setKey("New KEY");
		project.setName("New project name");

		when(projectFinderService.getById(command.id())).thenReturn(project);
		when(projectRepository.save(project)).thenReturn(project);

		Project updatedProject = projectService.update(command);

		assertEquals("KEY", updatedProject.getKey());
		assertEquals("Project name", updatedProject.getName());
		verify(projectRepository).save(project);
	}
}
