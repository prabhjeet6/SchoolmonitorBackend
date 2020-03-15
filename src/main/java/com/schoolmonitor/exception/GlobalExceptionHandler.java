 package com.schoolmonitor.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.schoolmonitor.model.ApiError;

/**
 * @author PrabhjeetS
 * @version 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	ApiError apiError;

	@ExceptionHandler({ BadCredentialsException.class, UsernameNotFoundException.class, SchoolMonitorException.class })
	public final ResponseEntity<?> handleException(Throwable ex, WebRequest request) {
		HttpHeaders headers = new HttpHeaders();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		GenericExceptionContainer<?> genericExceptionContainer = new GenericExceptionContainer<>(ex);
		return handleException(headers, genericExceptionContainer, status, request);

	}

	private ResponseEntity<ApiError> handleException(HttpHeaders headers, GenericExceptionContainer<?> exception,
			HttpStatus status, WebRequest request) {
		   apiError.setHttpStatusValue(status.value());
		   apiError.setHttpStatusCode(status.name());
		   apiError.setExceptionMessage(exception.toString());
		   apiError.setWebRequest(request.toString());
		   apiError.setParameterMap(request.getParameterMap());
		   apiError.setCause(((Throwable)exception.getExceptionObject()).getCause());
		   apiError.setStacktrace(((Throwable)exception.getExceptionObject()).getStackTrace());
		   
		return new ResponseEntity<>(apiError, headers, status);
	}

}