package com.bookstore.controller.exception.handlers;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bookstore.controller.exception.EntityValidationException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@ControllerAdvice
public class EntityValidationAdvice {
	
	@ResponseBody
	@ExceptionHandler(EntityValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	Map<String, Map<String, List<String>>> EntityValidationAdvice(EntityValidationException ex) {
		
		Type validationErrorsType = new TypeToken<Map<String, List<String>>>() {}.getType();
		
		Map<String, Map<String, List<String>>> errors = new HashMap<>();
		
		errors.put("errors", new Gson().fromJson(ex.getMessage(), validationErrorsType));
		
		return errors;
	}

}
