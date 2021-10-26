package com.example.demo.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.model.ErrorMessage;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);

//	@ExceptionHandler(Exception.class)
//	public ResponseEntity handleGlobalParentException(HttpRequest reqest, Exception e) {
//
//		
//		logger.error("Some Error occurrec {}",e.getMessage());
//		
//		return new ResponseEntity("Some error occurred, please try again", HttpStatus.METHOD_FAILURE);
//	}
	
	
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorMessage> handleInternalServerError(HttpServletRequest request, Exception ex) {
	        logger.error("handleInternalServerError ", request.getRequestURI(), ex);

	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ErrorMessage(LocalDateTime.now(), "My Internal server error", request.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
	    }

}
