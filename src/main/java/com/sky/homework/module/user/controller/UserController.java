package com.sky.homework.module.user.controller;

import com.sky.homework.module.user.controller.dto.mapper.UserMapper;
import com.sky.homework.module.user.controller.dto.request.AssignProjectRequest;
import com.sky.homework.module.user.controller.dto.request.CreateUserRequest;
import com.sky.homework.module.user.controller.dto.request.UpdateUserRequest;
import com.sky.homework.module.user.controller.dto.response.UserResponse;
import com.sky.homework.module.user.entity.User;
import com.sky.homework.module.user.service.UserFinderService;
import com.sky.homework.module.user.service.UserService;
import com.sky.homework.module.user.service.command.AssignProjectCommand;
import com.sky.homework.module.user.service.command.CreateUserCommand;
import com.sky.homework.module.user.service.command.DeleteUserCommand;
import com.sky.homework.module.user.service.command.UpdateUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final UserFinderService userFinderService;
	private final UserMapper userMapper;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public UserResponse create(@RequestBody CreateUserRequest request) {
		final User user = userService.create(
				new CreateUserCommand(request.email(), request.password(), request.name()));
		return userMapper.map(user);
	}

	@PutMapping(value = "/{id}")
	public UserResponse update(@PathVariable UUID id, @RequestBody UpdateUserRequest request) {
		final User user = userService.update(new UpdateUserCommand(id, request.email(), request.name()));
		return userMapper.map(user);
	}

	@GetMapping(value = "/{id}")
	public UserResponse get(@PathVariable UUID id) {
		return userMapper.map(userFinderService.getById(id));
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable UUID id) {
		userService.delete(new DeleteUserCommand(id));
	}

	@PostMapping(value = "/{userId}/projects")
	public UserResponse assignProject(@PathVariable UUID userId, @RequestBody AssignProjectRequest request) {
		return userMapper.map(userService.assignProject(new AssignProjectCommand(userId, request.projectId())));
	}
}
