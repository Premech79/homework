package com.sky.homework.module.user.controller.dto.response;

import com.sky.homework.module.project.controller.response.ProjectResponse;

import java.util.List;
import java.util.UUID;

public record UserResponse(

		UUID id,

		String email,

		String name,

		List<ProjectResponse> projects
) {
}
