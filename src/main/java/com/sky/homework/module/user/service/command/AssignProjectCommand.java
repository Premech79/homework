package com.sky.homework.module.user.service.command;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AssignProjectCommand(

		@NotNull
		UUID userId,

		@NotNull
		UUID projectId
) {
}
