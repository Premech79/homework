package com.sky.homework.common.config;

import com.sky.homework.common.dto.response.ErrorResponse;
import com.sky.homework.common.exception.ApiException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
		return handleException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ApiException.class)
	public final ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
		final ResponseStatus status = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);

		if (status != null) {
			return handleException(ex.getMessage(), status.code());
		}

		return handleException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	protected ResponseEntity<ErrorResponse> handleException(String messages, HttpStatus status) {
		return new ResponseEntity<>(new ErrorResponse(status, messages), new HttpHeaders(), status);
	}
}
