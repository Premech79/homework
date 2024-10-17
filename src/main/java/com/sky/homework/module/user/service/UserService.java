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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Validated
public class UserService {

	private final UserFinderService userFinderService;
	private final UserRepository userRepository;
	private final ProjectFinderService projectFinderService;
	private final PasswordService passwordService;

	@Transactional
	public User create(@Valid CreateUserCommand command) {
		if (userFinderService.existsByEmail(command.email())) {
			throw new EmailExistsException();
		}

		User user = new User();
		user.setId(UUID.randomUUID());
		user.setEmail(command.email());
		user.setPassword(passwordService.hashPassword(command.password()));
		user.setName(command.name());

		return userRepository.save(user);
	}

	@Transactional
	public User update(@Valid UpdateUserCommand command) {
		if (userFinderService.existsByEmail(command.email())) {
			throw new EmailExistsException();
		}

		User user = userFinderService.getById(command.id());
		user.setEmail(command.email());
		user.setName(command.name());

		return userRepository.save(user);
	}

	@Transactional
	public void delete(@Valid DeleteUserCommand command) {
		userRepository.delete(userFinderService.getById(command.id()));
	}

	@Transactional
	public User assignProject(@Valid AssignProjectCommand command) {
		User user = userFinderService.getById(command.userId());
		Project project = projectFinderService.getById(command.projectId());
		if (user.getProjects().contains(project)) {
			throw new ProjectAlreadyAssignedException();
		}
		user.getProjects().add(project);
		return userRepository.save(user);
	}
}
