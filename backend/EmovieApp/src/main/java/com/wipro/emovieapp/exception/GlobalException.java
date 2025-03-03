package com.wipro.emovieapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(MovieNotFoundException.class)
	public ResponseEntity<String> customException(String msg){
		
		return new ResponseEntity(msg,HttpStatus.OK);
		
	}

}
