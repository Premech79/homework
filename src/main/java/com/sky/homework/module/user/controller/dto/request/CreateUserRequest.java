package com.sky.homework.module.user.controller.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(

		@NotNull
		@NotBlank
		String email,

		@NotNull
		@NotBlank
		String password,

		@Nullable
		String name
) {
}
