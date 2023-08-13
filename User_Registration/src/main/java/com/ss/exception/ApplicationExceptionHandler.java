package com.ss.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.ss.response.GeneralResponse;

@RestControllerAdvice
public class ApplicationExceptionHandler

{
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) 
	{
		Map<String, String> errormap = new HashMap<>();
		
		ex.getBindingResult().getFieldErrors().forEach(error ->
		{
			errormap.put(error.getField(), error.getDefaultMessage());
		}
		);

		return errormap;

	}
	
	@ExceptionHandler(InternalException.class)
	public ResponseEntity<GeneralResponse> internalException(InternalException e) {
		LocalDateTime current = LocalDateTime.now();
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(e.getMessage()+"  "+current),HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(NullValueException.class)
	public ResponseEntity<GeneralResponse> nullValueException(NullValueException e) {
		LocalDateTime current = LocalDateTime.now();
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(e.getMessage()+"  "+current),HttpStatus.NOT_FOUND);

	}
	

	@ExceptionHandler(EmptyResultDataAccessExceptionHandle.class)
	public ResponseEntity<GeneralResponse> emptyResultDataAccessException(EmptyResultDataAccessExceptionHandle e) {
		LocalDateTime current = LocalDateTime.now();
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(e.getMessage()+"  "+current),HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(NoSuchElementExceptionHandle.class)
	public ResponseEntity<GeneralResponse> noSuchElementException(NoSuchElementExceptionHandle e) {
		LocalDateTime current = LocalDateTime.now();
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(e.getMessage()+"  "+current),HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(BadCredentialsExceptionHandle.class)
	public ResponseEntity<GeneralResponse> badCredentialsExceptionHandle(BadCredentialsExceptionHandle e) {
		LocalDateTime current = LocalDateTime.now();
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(e.getMessage()+"  "+current),HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(InvalidProperty.class)
	public ResponseEntity<GeneralResponse> propertyInvalidDataAccessApiUsageException(InvalidProperty e) {
		LocalDateTime current = LocalDateTime.now();
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(e.getMessage()+"  "+current),HttpStatus.BAD_REQUEST);

	}
	
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<GeneralResponse> handleAccessDeniedException(AccessDeniedException e) 
	{LocalDateTime current = LocalDateTime.now();
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(e.getMessage()+"  "+current),HttpStatus.FORBIDDEN);
	}

}
