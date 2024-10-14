package com.sky.homework.module.project.service.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProjectCommand(

		@NotNull
		@NotBlank
		String key,

		@NotNull
		@NotBlank
		String name

) {
}
