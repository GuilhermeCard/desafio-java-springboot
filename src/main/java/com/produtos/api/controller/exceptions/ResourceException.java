package com.produtos.api.controller.exceptions;

import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResourceException extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StandardError> badRequest(ConstraintViolationException e) {
		StandardError err = new StandardError();
		err.setStatus_code(HttpStatus.BAD_REQUEST.value());
		err.setMessage("Requisição inválida, verifique os campos digitados");

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<StandardError> notFound(NoSuchElementException e) {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
