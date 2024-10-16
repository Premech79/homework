package com.sky.homework.module.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sky.homework.module.user.controller.dto.mapper.UserMapper;
import com.sky.homework.module.user.controller.dto.request.CreateUserRequest;
import com.sky.homework.module.user.controller.dto.request.UpdateUserRequest;
import com.sky.homework.module.user.controller.dto.response.UserResponse;
import com.sky.homework.module.user.entity.User;
import com.sky.homework.module.user.service.UserFinderService;
import com.sky.homework.module.user.service.UserService;
import com.sky.homework.module.user.service.command.CreateUserCommand;
import com.sky.homework.module.user.service.command.DeleteUserCommand;
import com.sky.homework.module.user.service.command.UpdateUserCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private UserFinderService userFinderService;

	@MockBean
	private UserMapper userMapper;

	private ObjectMapper objectMapper;
	private UserResponse userResponse;
	private User user;

	@BeforeEach
	public void setUp() {
		objectMapper = new ObjectMapper();
		user = new User();
		//userResponse = new UserResponse();
	}

	@Test
	public void shouldCreateUser() throws Exception {
		CreateUserRequest request = new CreateUserRequest("user@example.com", "password", "Test User");
		given(userService.create(any(CreateUserCommand.class))).willReturn(user);
		given(userMapper.map(any(User.class))).willReturn(userResponse);

		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

		ArgumentCaptor<CreateUserCommand> commandCaptor = ArgumentCaptor.forClass(CreateUserCommand.class);
		verify(userService).create(commandCaptor.capture());
	}

	@Test
	public void shouldUpdateUser() throws Exception {
		UUID userId = UUID.randomUUID();
		UpdateUserRequest request = new UpdateUserRequest("new-email@example.com", "password", "New Name");
		given(userService.update(any(UpdateUserCommand.class))).willReturn(user);
		given(userMapper.map(any(User.class))).willReturn(userResponse);

		mockMvc.perform(put("/users/{id}", userId).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

		ArgumentCaptor<UpdateUserCommand> commandCaptor = ArgumentCaptor.forClass(UpdateUserCommand.class);
		verify(userService).update(commandCaptor.capture());
	}

	@Test
	public void shouldGetUser() throws Exception {
		UUID userId = UUID.randomUUID();
		given(userFinderService.getById(userId)).willReturn(user);
		given(userMapper.map(any(User.class))).willReturn(userResponse);

		mockMvc.perform(get("/users/{id}", userId))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

		verify(userFinderService).getById(userId);
	}

	@Test
	public void shouldDeleteUser() throws Exception {
		UUID userId = UUID.randomUUID();

		mockMvc.perform(delete("/users/{id}", userId)).andExpect(status().isNoContent());

		ArgumentCaptor<DeleteUserCommand> commandCaptor = ArgumentCaptor.forClass(DeleteUserCommand.class);
		verify(userService).delete(commandCaptor.capture());

	}
}