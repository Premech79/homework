package com.sky.homework.module.user.controller.dto.mapper;

import com.sky.homework.module.user.controller.dto.response.UserResponse;
import com.sky.homework.module.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

	public UserResponse map(User user) {
		return new UserResponse(user.getEmail(), user.getName());
	}
}
