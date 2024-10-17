package com.sky.homework.module.user.service;

import com.sky.homework.common.security.PasswordService;
import com.sky.homework.module.project.entity.Project;
import com.sky.homework.module.project.service.ProjectFinderService;
import com.sky.homework.module.user.entity.User;
import com.sky.homework.module.user.exception.EmailExistsException;
import com.sky.homework.module.user.exception.ProjectAlreadyAssignedException;
import com.sky.homework.module.user.service.command.AssignProjectCommand;
import com.sky.homework.module.user.service.command.CreateUserCommand;
import com.sky.homework.module.user.service.command.DeleteUserCommand;
import com.sky.homework.module.user.service.command.UpdateUserCommand;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

	private final UserFinderService userFinderService = mock(UserFinderService.class);
	private final UserRepository userRepository = mock(UserRepository.class);
	private final ProjectFinderService projectFinderService = mock(ProjectFinderService.class);
	private final PasswordService passwordService = mock(PasswordService.class);

	private final UserService userService = new UserService(userFinderService, userRepository, projectFinderService,
			passwordService);

	@Test
	void create_user_shouldThrowException_whenEmailExists() {
		CreateUserCommand command = new CreateUserCommand("test@example.com", "password", "Test User");
		when(userFinderService.existsByEmail(command.email())).thenReturn(true);

		assertThrows(EmailExistsException.class, () -> userService.create(command));
	}

	@Test
	void create_user_shouldSaveAndReturnUser_whenValid() {
		CreateUserCommand command = new CreateUserCommand("test@example.com", "password", "Test User");
		when(userFinderService.existsByEmail(command.email())).thenReturn(false);
		when(passwordService.hashPassword(command.password())).thenReturn("hashedPassword");
		User user = new User();
		user.setId(UUID.randomUUID());
		user.setEmail(command.email());
		user.setPassword("hashedPassword");
		user.setName(command.name());
		when(userRepository.save(any(User.class))).thenReturn(user);

		User createdUser = userService.create(command);

		assertEquals("test@example.com", createdUser.getEmail());
		assertEquals("hashedPassword", createdUser.getPassword());
		assertEquals("Test User", createdUser.getName());
		verify(userRepository).save(any(User.class));
	}

	@Test
	void update_user_shouldThrowException_whenEmailExists() {
		UpdateUserCommand command = new UpdateUserCommand(UUID.randomUUID(), "test@example.com", "New Name");
		when(userFinderService.existsByEmail(command.email())).thenReturn(true);

		assertThrows(EmailExistsException.class, () -> userService.update(command));
	}

	@Test
	void update_user_shouldSaveAndReturnUpdatedUser_whenValid() {
		UpdateUserCommand command = new UpdateUserCommand(UUID.randomUUID(), "test@example.com", "Updated Name");
		when(userFinderService.existsByEmail(command.email())).thenReturn(false);
		User existingUser = new User();
		existingUser.setEmail("oldemail@example.com");
		existingUser.setName("Old Name");
		when(userFinderService.getById(command.id())).thenReturn(existingUser);
		when(userRepository.save(existingUser)).thenReturn(existingUser);

		User updatedUser = userService.update(command);

		assertEquals("test@example.com", updatedUser.getEmail());
		assertEquals("Updated Name", updatedUser.getName());
		verify(userRepository).save(existingUser);
	}

	@Test
	void delete_shouldDeleteUser_whenValid() {
		DeleteUserCommand command = new DeleteUserCommand(UUID.randomUUID());
		User userToDelete = new User();
		when(userFinderService.getById(command.id())).thenReturn(userToDelete);

		userService.delete(command);

		verify(userRepository).delete(userToDelete);
	}

	@Test
	void assignProject_shouldThrowException_whenProjectAlreadyAssigned() {
		AssignProjectCommand command = new AssignProjectCommand(UUID.randomUUID(), UUID.randomUUID());
		User user = new User();
		Project project = new Project();
		project.setId(command.projectId());
		user.getProjects().add(project);
		when(userFinderService.getById(command.userId())).thenReturn(user);
		when(projectFinderService.getById(command.projectId())).thenReturn(project);

		assertThrows(ProjectAlreadyAssignedException.class, () -> userService.assignProject(command));
	}

	@Test
	void assignProject_shouldAddProjectToUser_whenNotAlreadyAssigned() {
		AssignProjectCommand command = new AssignProjectCommand(UUID.randomUUID(), UUID.randomUUID());
		User user = new User();
		Project project = new Project();
		project.setId(command.projectId());
		when(userFinderService.getById(command.userId())).thenReturn(user);
		when(projectFinderService.getById(command.projectId())).thenReturn(project);
		when(userRepository.save(user)).thenReturn(user);

		User resultUser = userService.assignProject(command);

		assertTrue(resultUser.getProjects().contains(project));
		verify(userRepository).save(user);
	}
}
