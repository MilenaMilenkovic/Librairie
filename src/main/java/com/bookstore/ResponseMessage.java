package com.bookstore;

public class ResponseMessage {
	private String message;

	public ResponseMessage(String message) {
		super();
		this.message = message;
	}
	
	public static ResponseMessage notFound() {
		return new ResponseMessage("Record not found.");
	}
	
	public String getMessage() {
		return message;
	}
}
