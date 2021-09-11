package com.bookstore.controller.exception;

public class InvalidBookSearchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2139004505988401347L;
	
	public InvalidBookSearchException() {
		super("Invalid book search parameters.");
	}

}
