package com.publicis.sapient.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomErrorAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ApiException.class)
	public final ResponseEntity<Object> handleAllExceptions(ApiException ex) {
		ErrorResponse error = new ErrorResponse(ex.getStatus(), ex.getMessage());
		return new ResponseEntity<>(error, ex.getStatus());
	}
}
