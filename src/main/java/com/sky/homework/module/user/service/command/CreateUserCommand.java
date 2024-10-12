package com.sky.homework.module.user.service.command;

public record CreateUserCommand(

		String email,

		String password,

		String name
) {
}
