package com.sky.homework.common.config;

import com.sky.homework.common.dto.response.ErrorResponse;
import com.sky.homework.common.exception.ApiException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ApiException.class)
	public final ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
		final ResponseStatus status = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);

		if (status != null) {
			return handleException(ex.getMessage(), status.code());
		}

		return handleException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<ErrorResponse> handleConstraintException(ConstraintViolationException ex) {
		Set<String> output = ex.getConstraintViolations()
				.stream()
				.map(v -> v.getPropertyPath() + " " + v.getMessage())
				.collect(Collectors.toSet());

		return handleException(output, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<ErrorResponse> handleAclException() {
		return handleException("Access denied", HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponse> handleOtherExceptions(Exception ex) {
		return handleException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	protected ResponseEntity<ErrorResponse> handleException(String messages, HttpStatus status) {
		return handleException(Collections.singleton(messages), status);
	}

	protected ResponseEntity<ErrorResponse> handleException(Collection<String> messages, HttpStatus status) {
		return new ResponseEntity<>(new ErrorResponse(messages), new HttpHeaders(), status);
	}
}
