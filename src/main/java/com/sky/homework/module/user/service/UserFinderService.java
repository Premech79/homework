package com.sky.homework.module.user.service;

import com.sky.homework.module.user.entity.User;
import com.sky.homework.module.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserFinderService {

	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Transactional(readOnly = true)
	public User getById(UUID id) {
		return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
	}
}
