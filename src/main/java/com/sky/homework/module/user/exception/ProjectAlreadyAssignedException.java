package com.sky.homework.module.user.exception;

import com.sky.homework.common.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ProjectAlreadyAssignedException extends ApiException {

	@Override
	public String getMessage() {
		return "Project already assigned to user";
	}
}
