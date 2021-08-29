package com.bookstore;

import java.io.Serializable;

import com.google.gson.Gson;

public class ResponseMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 118103247088212492L;
	private String message;

	public ResponseMessage(String message) {
		super();
		this.message = message;
	}
	
	public static String json(String m) {
		ResponseMessage message = new ResponseMessage(m);
		
		return new Gson().toJson(message);
	}
	
	public String getMessage() {
		return message;
	}
}
