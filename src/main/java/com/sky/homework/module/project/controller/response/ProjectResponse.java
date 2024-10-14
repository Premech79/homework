package com.sky.homework.module.project.controller.response;

import java.util.UUID;

public record ProjectResponse(

		UUID id,

		String key,

		String name
) {
}
