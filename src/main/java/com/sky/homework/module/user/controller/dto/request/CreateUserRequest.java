package com.sky.homework.module.user.controller.dto.request;

public record CreateUserRequest(

		String email,

		String password,

		String name
) {
}
