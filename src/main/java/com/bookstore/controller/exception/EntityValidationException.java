package com.bookstore.controller.exception;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class EntityValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -189315769182784246L;
	
	public EntityValidationException(Map<String, List<String>> errors) {
		super(new Gson().toJson(errors));
	}

}
