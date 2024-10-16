package com.sky.homework.module.user.exception;

import com.sky.homework.common.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EmailExistsException extends ApiException {

	@Override
	public String getMessage() {
		return "Email already exists";
	}
}
