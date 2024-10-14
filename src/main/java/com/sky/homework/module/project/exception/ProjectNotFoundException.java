package com.sky.homework.module.project.exception;

import com.sky.homework.common.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProjectNotFoundException extends ApiException {

	@Override
	public String getMessage() {
		return "Project not found";
	}

}
