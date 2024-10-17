package com.sky.homework.module.user.service;

import com.sky.homework.module.user.entity.User;
import com.sky.homework.module.user.exception.UserNotFoundException;
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
class UserFinderServiceIT {

	@Autowired
	private UserFinderService userFinderService;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void setupDatabase() {
		User user = new User();
		user.setId(UUID.fromString("35c59ffe-7817-4481-9411-71c165f65d8a"));
		user.setName("Test User");
		user.setPassword("password");
		user.setEmail("test@example.com");
		userRepository.save(user);
	}

	@Test
	void existsByEmail_shouldReturnTrue_whenEmailExists() {
		boolean exists = userFinderService.existsByEmail("test@example.com");
		assertTrue(exists, "User should exist by email");
	}

	@Test
	void existsByEmail_shouldReturnFalse_whenEmailDoesNotExist() {
		boolean exists = userFinderService.existsByEmail("nonexistent@example.com");
		assertFalse(exists, "User should not exist by this email");
	}

	@Test
	void getById_shouldReturnUser_whenUserExists() {
		UUID userId = UUID.fromString("35c59ffe-7817-4481-9411-71c165f65d8a");

		User user = userFinderService.getById(userId);

		assertNotNull(user, "User should not be null");
		assertEquals("test@example.com", user.getEmail(), "User's email should match");
	}

	@Test
	void getById_shouldThrowException_whenUserDoesNotExist() {
		UUID nonExistentId = UUID.randomUUID();

		assertThrows(UserNotFoundException.class, () -> userFinderService.getById(nonExistentId),
				"Expected UserNotFoundException");
	}
}
