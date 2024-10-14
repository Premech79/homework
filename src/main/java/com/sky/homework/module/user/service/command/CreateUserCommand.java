package com.sky.homework.module.user.service.command;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserCommand(

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
