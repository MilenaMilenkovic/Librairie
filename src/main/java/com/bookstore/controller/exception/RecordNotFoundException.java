package com.bookstore.controller.exception;

public class RecordNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3722396185277100208L;

	public RecordNotFoundException() {
		super("Invalid search parameters.");
	}
}
