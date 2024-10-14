package com.sky.homework.module.project.service.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateProjectCommand(

		@NotNull
		UUID id,

		@NotNull
		@NotBlank
		String key,

		@NotNull
		@NotBlank
		String name
) {
}
