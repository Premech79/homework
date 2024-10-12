package com.sky.homework.module.user.service;

import com.sky.homework.module.user.entity.User;
import com.sky.homework.module.user.exception.EmailExistsException;
import com.sky.homework.module.user.service.command.CreateUserCommand;
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
}
