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
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private UserService userService;

	@MockBean
	private UserFinderService userFinderService;

	@MockBean
	private UserMapper userMapper;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@SneakyThrows
	public void shouldCreateUser() {
		User user = new User();
		user.setId(UUID.fromString("e5a7962f-0986-4f39-bd56-513ad6440d7b"));
		user.setName("Test User");
		user.setEmail("user@example.com");
		user.setPassword("password");

		UserResponse userResponse = new UserResponse(
				UUID.fromString("e5a7962f-0986-4f39-bd56-513ad6440d7b"),
				"user@example.com",
				"Test User",
				List.of()
		);

		CreateUserRequest request = new CreateUserRequest(
				"user@example.com",
				"password",
				"Test User");

		given(userService.create(any(CreateUserCommand.class))).willReturn(user);
		given(userMapper.map(any(User.class))).willReturn(userResponse);

		mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is("e5a7962f-0986-4f39-bd56-513ad6440d7b")))
				.andExpect(jsonPath("$.email", is("user@example.com")))
				.andExpect(jsonPath("$.name", is("Test User")));

		ArgumentCaptor<CreateUserCommand> commandCaptor = ArgumentCaptor.forClass(CreateUserCommand.class);
		verify(userService).create(commandCaptor.capture());
	}

	@Test
	@SneakyThrows
	public void shouldUpdateUser() {
		User user = new User();
		user.setId(UUID.fromString("e5a7962f-0986-4f39-bd56-513ad6440d7b"));
		user.setName("New Name");
		user.setEmail("new-email@example.com");
		user.setPassword("password");

		UserResponse userResponse = new UserResponse(
				UUID.fromString("e5a7962f-0986-4f39-bd56-513ad6440d7b"),
				"new-email@example.com",
				"New Name",
				List.of()
		);

		UUID userId = UUID.randomUUID();
		UpdateUserRequest request = new UpdateUserRequest("new-email@example.com", "password", "New Name");
		given(userService.update(any(UpdateUserCommand.class))).willReturn(user);
		given(userMapper.map(any(User.class))).willReturn(userResponse);

		mockMvc.perform(put("/users/{id}", userId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is("e5a7962f-0986-4f39-bd56-513ad6440d7b")))
				.andExpect(jsonPath("$.email", is("new-email@example.com")))
				.andExpect(jsonPath("$.name", is("New Name")));

		ArgumentCaptor<UpdateUserCommand> commandCaptor = ArgumentCaptor.forClass(UpdateUserCommand.class);
		verify(userService).update(commandCaptor.capture());
	}

	@Test
	public void shouldGetUser() throws Exception {
		User user = new User();
		user.setId(UUID.fromString("e5a7962f-0986-4f39-bd56-513ad6440d7b"));
		user.setName("Test User");
		user.setEmail("user@example.com");
		user.setPassword("password");

		UserResponse userResponse = new UserResponse(
				UUID.fromString("e5a7962f-0986-4f39-bd56-513ad6440d7b"),
				"user@example.com",
				"Test User",
				List.of()
		);

		UUID userId = UUID.randomUUID();
		given(userFinderService.getById(userId)).willReturn(user);
		given(userMapper.map(any(User.class))).willReturn(userResponse);

		mockMvc.perform(get("/users/{id}", userId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is("e5a7962f-0986-4f39-bd56-513ad6440d7b")))
				.andExpect(jsonPath("$.email", is("user@example.com")))
				.andExpect(jsonPath("$.name", is("Test User")));

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