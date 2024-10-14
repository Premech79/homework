package com.sky.homework.module.user.service;

import com.sky.homework.module.user.entity.User;
import com.sky.homework.module.user.exception.EmailExistsException;
import com.sky.homework.module.user.service.command.CreateUserCommand;
import com.sky.homework.module.user.service.command.DeleteUserCommand;
import com.sky.homework.module.user.service.command.UpdateUserCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class UserService {

	private final UserFinderService userFinderService;
	private final UserRepository userRepository;

	@Transactional
	public User create(@Valid CreateUserCommand command) {
		if (userFinderService.existsByEmail(command.email())) {
			throw new EmailExistsException();
		}

		User user = new User();
		user.setEmail(command.email());
		user.setPassword(command.password());
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
}
