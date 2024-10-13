package com.sky.homework.module.user.service.command;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteUserCommand(

		@NotNull
		UUID id
) {
}
