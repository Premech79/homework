package com.sky.homework.module.user.controller.dto.request;

public record UpdateUserRequest(

		String email,

		String password,

		String name
) {
}
