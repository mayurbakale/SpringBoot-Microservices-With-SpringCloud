package com.udemy.springbootrestwebservice.exception;

import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoCarsFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8800255845409059095L;
	
	public NoCarsFoundException(String message) {
		super(message);
	}

}
