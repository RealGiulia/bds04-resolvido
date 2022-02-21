package com.devsuperior.bds04.services.exceptions;

public class MethodArgumentNotValidException extends RuntimeException {
	
	
private static final long serialVersionUID = 50L;
	
	public MethodArgumentNotValidException(String msg) {
		super(msg);
	}

}
