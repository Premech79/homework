package com.sky.homework.module.user.service;

import com.sky.homework.module.project.entity.Project;
import com.sky.homework.module.project.service.ProjectService;
import com.sky.homework.module.project.service.command.CreateProjectCommand;
import com.sky.homework.module.user.entity.User;
import com.sky.homework.module.user.exception.EmailExistsException;
import com.sky.homework.module.user.exception.ProjectAlreadyAssignedException;
import com.sky.homework.module.user.service.command.AssignProjectCommand;
import com.sky.homework.module.user.service.command.CreateUserCommand;
import com.sky.homework.module.user.service.command.DeleteUserCommand;
import com.sky.homework.module.user.service.command.UpdateUserCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@Rollback
class UserServiceIT {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserFinderService userFinderService;

	@Autowired
	private ProjectService projectService;

	@Test
	void create_shouldCreateAndReturnUser_whenCommandIsValid() {
		CreateUserCommand command = new CreateUserCommand("newuser@example.com", "password123", "New User");

		User createdUser = userService.create(command);

		assertNotNull(createdUser.getId());
		assertEquals("newuser@example.com", createdUser.getEmail());
		assertEquals("New User", createdUser.getName());
		assertTrue(userFinderService.existsByEmail("newuser@example.com"));
	}

	@Test
	void create_shouldThrowException_whenEmailAlreadyExists() {
		CreateUserCommand command = new CreateUserCommand("existinguser@example.com", "password123", "Existing User");
		userService.create(command);

		CreateUserCommand conflictCommand = new CreateUserCommand("existinguser@example.com", "newpassword456",
				"New User Test");
		assertThrows(EmailExistsException.class, () -> userService.create(conflictCommand));
	}

	@Test
	void update_shouldReturnUpdatedUser_whenUserExists() {
		User existingUser = new User();
		existingUser.setId(UUID.randomUUID());
		existingUser.setName("Initial User");
		existingUser.setEmail("updateuser@example.com");
		existingUser.setPassword("password");
		userRepository.save(existingUser);

		UpdateUserCommand command = new UpdateUserCommand(existingUser.getId(), "updateduser@example.com",
				"Updated User");

		User updatedUser = userService.update(command);

		assertEquals("updateduser@example.com", updatedUser.getEmail());
		assertEquals("Updated User", updatedUser.getName());
	}

	@Test
	void delete_shouldRemoveUser_whenUserExists() {
		User existingUser = new User();
		existingUser.setId(UUID.randomUUID());
		existingUser.setName("Initial User");
		existingUser.setEmail("updateuser@example.com");
		existingUser.setPassword("password");
		userRepository.save(existingUser);

		DeleteUserCommand command = new DeleteUserCommand(existingUser.getId());

		userService.delete(command);

		assertFalse(userFinderService.existsByEmail("deleteuser@example.com"));
	}

	@Test
	void assignProject_shouldAddProjectToUser_whenNotAlreadyAssigned() {
		User user = new User();
		user.setId(UUID.randomUUID());
		user.setName("Initial User");
		user.setEmail("updateuser@example.com");
		user.setPassword("password");
		userRepository.save(user);

		Project project = projectService.create(new CreateProjectCommand(
				"KEY",
				"Project name"
		));

		AssignProjectCommand command = new AssignProjectCommand(user.getId(), project.getId());

		User updatedUser = userService.assignProject(command);

		assertTrue(updatedUser.getProjects().contains(project));
	}

	@Test
	void assignProject_shouldThrowException_whenProjectAlreadyAssigned() {
		Project project = projectService.create(new CreateProjectCommand(
				"KEY",
				"Project name"
		));

		User user = new User();
		user.setId(UUID.randomUUID());
		user.setName("Initial User");
		user.setEmail("updateuser@example.com");
		user.setPassword("password");
		user.getProjects().add(project);
		userRepository.save(user);

		AssignProjectCommand command = new AssignProjectCommand(user.getId(), project.getId());

		assertThrows(ProjectAlreadyAssignedException.class, () -> userService.assignProject(command));
	}
}
