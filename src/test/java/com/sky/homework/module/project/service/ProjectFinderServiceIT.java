package com.sky.homework.module.project.service;

import com.sky.homework.module.project.entity.Project;
import com.sky.homework.module.project.exception.ProjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class ProjectFinderServiceIT {

	@Autowired
	private ProjectFinderService projectFinderService;

	@Autowired
	private ProjectRepository projectRepository;

	@BeforeEach
	void setupDatabase() {
		Project project = new Project();
		project.setId(UUID.fromString("32639997-9a96-4835-91cf-19f89259fceb"));
		project.setKey("KEY");
		project.setName("Project name");
		projectRepository.save(project);
	}

	@Test
	void existsByKey_shouldReturnTrue_whenKeyExists() {
		boolean exists = projectFinderService.existsByKey("KEY");
		assertTrue(exists, "Project should exist by key");
	}

	@Test
	void existsByKey_shouldReturnFalse_whenKeyDoesNotExist() {
		boolean exists = projectFinderService.existsByKey("Unknown KEY");
		assertFalse(exists, "Project should not exist by this key");
	}

	@Test
	void getById_shouldReturnUser_whenUserExists() {
		UUID id = UUID.fromString("32639997-9a96-4835-91cf-19f89259fceb");

		Project project = projectFinderService.getById(id);

		assertNotNull(project, "Project should not be null");
		assertEquals("KEY", project.getKey(), "Project's key should match");
	}

	@Test
	void getById_shouldThrowException_whenUserDoesNotExist() {
		UUID nonExistentId = UUID.randomUUID();

		assertThrows(ProjectNotFoundException.class, () -> projectFinderService.getById(nonExistentId),
				"Expected ProjectNotFoundException");
	}
}
