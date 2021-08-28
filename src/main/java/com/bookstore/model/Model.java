package com.bookstore.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Transient;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

public abstract class Model {
	static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	@Transient
	private Map<String, List<String>> errors;
	
	public Map<String, List<String>> getErrors() {
		return this.errors;
	}
	
	public boolean isValid() {
		validate();
		return this.errors.isEmpty();
	}
	
	public void validate() {
		Set<ConstraintViolation<Model>> constraints = validator.validate(this);
		
		// Unreference previous validation errors
		errors = new HashMap<>();
		
		if (!constraints.isEmpty()) {
  	  		constraints.forEach(this::addError);
  	  	}
	}
	
	private void addError(ConstraintViolation<Model> constraint) {
		String property = constraint.getPropertyPath().toString();
		List<String> pErrors = errors.get(property);
		if(pErrors == null) {
			pErrors = new ArrayList<>();
		}
			
		pErrors.add(constraint.getMessage());
		errors.put(property, pErrors);
	}
}
