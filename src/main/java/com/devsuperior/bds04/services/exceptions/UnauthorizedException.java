package com.devsuperior.bds04.services.exceptions;

public class UnauthorizedException extends RuntimeException {
	
	
private static final long serialVersionUID = 50L;
	
	public UnauthorizedException(String msg) {
		super(msg);
	}
}
