package com.sky.homework.module.user.controller;

import com.sky.homework.module.user.controller.dto.mapper.UserMapper;
import com.sky.homework.module.user.controller.dto.request.CreateUserRequest;
import com.sky.homework.module.user.controller.dto.response.UserResponse;
import com.sky.homework.module.user.entity.User;
import com.sky.homework.module.user.service.UserFinderService;
import com.sky.homework.module.user.service.UserService;
import com.sky.homework.module.user.service.command.CreateUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final UserFinderService userFinderService;
	private final UserMapper userMapper;

	@PostMapping
	public UserResponse create(@RequestBody CreateUserRequest request) {
		final User user = userService.create(
				new CreateUserCommand(request.email(), request.password(), request.name()));
		return userMapper.map(user);
	}
}
