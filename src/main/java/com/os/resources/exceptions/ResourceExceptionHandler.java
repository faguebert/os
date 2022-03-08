package com.os.resources.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.os.service.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException e){
		StandardError error = new StandardError(System.currentTimeMillis(), 
				HttpStatus.NOT_FOUND.value(), e.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DataIntegratyViolationException.class)
	public ResponseEntity<StandardError> dataIntegrationViolationException(DataIntegratyViolationException e){
		StandardError error = new StandardError(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), e.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> objectNotFoundException(MethodArgumentNotValidException e){
		ValidationError error = new ValidationError(System.currentTimeMillis(), 
				HttpStatus.BAD_REQUEST.value(), "Erro na validação dos campos");
		
		for(FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			error.addErrors(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
}
