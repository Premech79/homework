package com.sky.homework.module.project.service;

import com.sky.homework.module.project.entity.Project;
import com.sky.homework.module.project.exception.ProjectKeyExistsException;
import com.sky.homework.module.project.service.command.CreateProjectCommand;
import com.sky.homework.module.project.service.command.UpdateProjectCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class ProjectServiceIT {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ProjectFinderService projectFinderService;

	@Test
	void create_shouldCreateAndReturnProject_whenCommandIsValid() {
		CreateProjectCommand command = new CreateProjectCommand("KEY", "Project name");

		Project project = projectService.create(command);

		assertNotNull(project.getId());
		assertEquals("KEY", project.getKey());
		assertEquals("Project name", project.getName());
		assertTrue(projectFinderService.existsByKey("KEY"));
	}

	@Test
	void create_shouldThrowException_whenKeyAlreadyExists() {
		CreateProjectCommand command = new CreateProjectCommand("KEY", "Project name");

		projectService.create(command);

		CreateProjectCommand conflictingCommand = new CreateProjectCommand("KEY", "Different project name ");

		assertThrows(ProjectKeyExistsException.class, () -> projectService.create(conflictingCommand));
	}

	@Test
	void update_shouldReturnUpdatedProject_whenProjectExists() {
		Project project = new Project();
		project.setId(UUID.fromString("7b3ef0d1-2122-44d2-9ae5-1c0cdbce4985"));
		project.setKey("KEY");
		project.setName("Project name");
		projectRepository.save(project);

		UpdateProjectCommand command = new UpdateProjectCommand(
				UUID.fromString("7b3ef0d1-2122-44d2-9ae5-1c0cdbce4985"),
				"New key",
				"New project name"
		);

		Project updatedProject = projectService.update(command);

		assertEquals("New key", updatedProject.getKey());
		assertEquals("New project name", updatedProject.getName());
	}
}
