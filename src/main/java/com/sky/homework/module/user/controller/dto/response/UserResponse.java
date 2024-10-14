package com.sky.homework.module.user.controller.dto.response;

import java.util.UUID;

public record UserResponse(

		UUID id,

		String email,

		String name
) {
}
