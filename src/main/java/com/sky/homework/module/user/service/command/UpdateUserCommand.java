package com.sky.homework.module.user.service.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateUserCommand(

		@NotNull
		UUID id,

		@NotNull
		@NotBlank
		String email,

		String name) {
}
