package br.com.marcos.cursoms.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.marcos.cursoms.services.exceptions.DataIntegrityService;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(
				ObjectNotFoundException e, HttpServletRequest request){
				StandardError error = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		}
	
	@ExceptionHandler(DataIntegrityService.class)
	public ResponseEntity<StandardError> dataIntegrity(
				DataIntegrityService e, HttpServletRequest request){
				StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> dataIntegrity(
			MethodArgumentNotValidException e, HttpServletRequest request){
				ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(), 
												"erro de validação", System.currentTimeMillis());
				for (FieldError  x : e.getBindingResult().getFieldErrors()) {
					error.addError(x.getField(), x.getDefaultMessage());
				}
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
