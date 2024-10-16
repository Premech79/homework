package com.sky.homework.module.user.controller.dto.mapper;

import com.sky.homework.module.project.controller.mapper.ProjectMapper;
import com.sky.homework.module.user.controller.dto.response.UserResponse;
import com.sky.homework.module.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

	private final ProjectMapper projectMapper;

	public UserResponse map(User user) {
		return new UserResponse(
				user.getId(),
				user.getEmail(),
				user.getName(),
				user.getProjects().stream().map(projectMapper::map).toList());
	}
}
