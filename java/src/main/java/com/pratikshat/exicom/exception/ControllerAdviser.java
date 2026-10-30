package com.pratikshat.exicom.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.google.auto.value.processor.escapevelocity.ParseException;


@ControllerAdvice
public class ControllerAdviser extends ResponseEntityExceptionHandler {
	
	private static final Logger logger=LoggerFactory.getLogger(ControllerAdviser.class);
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Object> handlebadCredentionalsException(BadCredentialsException ex,WebRequest request,HttpServletRequest httpRequest){
		
		Map<String, Object> body = new LinkedHashMap<String,Object>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Bad credentials");
        
        logger.error("Loggin Error {} ",httpRequest.getRequestURI(),ex);
        
        return new ResponseEntity<>(body,HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(UnauthoriseException.class)
	public ResponseEntity<Object> handlebadCredentionalsException(UnauthoriseException ex,WebRequest request,HttpServletRequest httpRequest){
		
		Map<String, Object> body = new LinkedHashMap<String,Object>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        
        logger.error("Error in calling {}",httpRequest.getRequestURI(),ex);
        
        return new ResponseEntity<>(body,HttpStatus.FORBIDDEN);
	}
	
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> handlebadCredentionalsException(BadRequestException ex,WebRequest request,HttpServletRequest httpRequest){
		
		Map<String, Object> body = new LinkedHashMap<String,Object>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        
        logger.error("Error in calling {}",httpRequest.getRequestURI(),ex);
        
        return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleMainException(Exception ex,WebRequest request,HttpServletRequest httpRequest){
		 Map<String, Object> body = new LinkedHashMap<String,Object>();
	     body.put("timestamp", LocalDateTime.now());
	     body.put("message", "Server error, Please contact IT Team");
	        
	     logger.error("Error in calling {}",httpRequest.getRequestURI(),ex);
	     
	     return new ResponseEntity<>(body,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NonUniqueResultException.class)
	public ResponseEntity<Object> handleNonUniqueException(NonUniqueResultException ex,WebRequest request,HttpServletRequest httpRequest){
		 Map<String, Object> body = new LinkedHashMap<String,Object>();
	     body.put("timestamp", LocalDateTime.now());
	     body.put("message", "Duplicate record found");
	        
	     logger.error("Error in calling {}",httpRequest.getRequestURI(),ex);
	     
	     return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(ParseException.class)
	public ResponseEntity<Object> handleParserException(ParseException ex,WebRequest request,HttpServletRequest httpRequest){
		 Map<String, Object> body = new LinkedHashMap<String,Object>();
	     body.put("timestamp", LocalDateTime.now());
	     body.put("message", "Unable to parse provided data");
	        
	     logger.error("Error in calling {}",httpRequest.getRequestURI(),ex);
	     
	     return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(PasswordComplexityException.class)
	public ResponseEntity<Object> handlePasswordException(PasswordComplexityException ex, WebRequest request, HttpServletRequest httpRequest) {
	    Map<String, Object> body = new LinkedHashMap<>();
	    body.put("timestamp", LocalDateTime.now());
	    body.put("message", ex.getMessage());

	    logger.error("Password complexity violation at {}", httpRequest.getRequestURI(), ex);

	    return new ResponseEntity<>(body, HttpStatus.PRECONDITION_REQUIRED);
	}

}
