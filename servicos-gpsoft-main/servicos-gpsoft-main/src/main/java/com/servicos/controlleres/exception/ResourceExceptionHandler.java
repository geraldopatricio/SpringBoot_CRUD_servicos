package com.servicos.controlleres.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.servicos.services.exception.ConstraintVException;
import com.servicos.services.exception.DataIntegrityException;
import com.servicos.services.exception.ObjectNFException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNFException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNFException e,
			HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(),
				HttpStatus.NOT_FOUND.value(), "Não encontrado", e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(ConstraintVException.class)
	public ResponseEntity<StandardError> objectNotFound(ConstraintVException e,
			HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(),
				HttpStatus.NOT_FOUND.value(), "Não encontrado", e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}	
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e,
			HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), "Integridade de dados", e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e,
			HttpServletRequest request) {

		ValidationError err = new ValidationError(System.currentTimeMillis(),
				HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", "Ocorreu um erro.",
				request.getRequestURI());
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}


	@ExceptionHandler(UnexpectedRollbackException.class)
	public ResponseEntity<StandardError> test(UnexpectedRollbackException ex,
			HttpServletRequest request) {
		StandardError err = new StandardError(System.currentTimeMillis(),
				HttpStatus.FORBIDDEN.value(), "Acesso negado", "Duplicado",
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);

	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<StandardError> test(EmptyResultDataAccessException ex,
			HttpServletRequest request) {
		StandardError err = new StandardError(System.currentTimeMillis(),
				HttpStatus.NOT_FOUND.value(), "Integridade de dados", ex.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);

	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StandardError> test(ConstraintViolationException ex,
			HttpServletRequest request) {
		if(ex.getMessage().contains("CNPJ inválido!")) {
			StandardError err = new StandardError(System.currentTimeMillis(),
					HttpStatus.NOT_FOUND.value(), "Integridade de dados", "CNPJ inválido!",
					request.getRequestURI());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
		}else {
			StandardError err = new StandardError(System.currentTimeMillis(),
					HttpStatus.NOT_FOUND.value(), "Integridade de dados", ex.getMessage(),
					request.getRequestURI());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
		}	

	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<StandardError> test(HttpMessageNotReadableException ex,
			HttpServletRequest request) {
		if((ex.getMessage().contains("JSON parse error: Cannot deserialize value of type `java.util.Date` from String"))
				&&(ex.getMessage().contains("expected format \"dd/MM/yyyy HH:mm:ss\"")) ) {
			StandardError err = new StandardError(System.currentTimeMillis(),
					HttpStatus.NOT_FOUND.value(), "Integridade de dados", "Data inválida!",
					request.getRequestURI());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
		}else {
			StandardError err = new StandardError(System.currentTimeMillis(),
					HttpStatus.NOT_FOUND.value(), "Integridade de dados", ex.getMessage(),
					request.getRequestURI());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
		}	

	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<StandardError> test(NoSuchElementException ex,
			HttpServletRequest request) {
		if(ex.getMessage().contains("No value present"))
				 {
			StandardError err = new StandardError(System.currentTimeMillis(),
					HttpStatus.NOT_FOUND.value(), "Integridade de dados", "Id inválido para algum campo fornecido!",
					request.getRequestURI());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
		}else {
			StandardError err = new StandardError(System.currentTimeMillis(),
					HttpStatus.NOT_FOUND.value(), "Integridade de dados", ex.getMessage(),
					request.getRequestURI());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
		}	

	}	
	
	//ok
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<StandardError> test(SQLIntegrityConstraintViolationException ex,
			HttpServletRequest request) {
		String errorMessage = null;
		if(ex.getMessage().contains("Duplicate")) {
			errorMessage = "Duplicado";
		}
		if(ex.getMessage().contains("Unique")) {
			errorMessage = "Duplicado";
		}
		if(ex.getMessage().contains("Cannot delete")) {
			errorMessage = "Não foi possivel deletar, está em uso por outro cadastro";
		}
		StandardError err = new StandardError(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), "Integridade de dados",errorMessage ,
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);

	}
	

	
	

}
