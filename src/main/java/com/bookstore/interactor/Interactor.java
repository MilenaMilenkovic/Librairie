package com.bookstore.interactor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public abstract class Interactor<T, R extends JpaRepository<T, Long>> {
	static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	@Autowired
	protected R repository;
	
	protected T subject;
	
	private Map<String, List<String>> errors;
	
	/*
	 * Returns validation errors if any
	 */
	public Map<String, List<String>> getErrors() {
		return this.errors;
	}
	
	/*
	 *  Manages Hibernate validations before saving
	 */
	public boolean save() {
		if (!this.isValid())
			return false;

		repository.save(subject);
		return true;
	}
		
	private boolean isValid() {
		validate();
		return this.errors.isEmpty();
	}
	
	/*
	 *  Collects Hibernate validation errors
	 */
	private void validate() {
		Set<ConstraintViolation<T>> constraints = validator.validate(this.subject);
		
		// Unreference previous validation errors
		errors = new HashMap<>();
		
		if (!constraints.isEmpty()) {
  	  		constraints.forEach(this::addError);
  	  	}
	}
	
	private void addError(ConstraintViolation<T> constraint) {
		String property = constraint.getPropertyPath().toString();
		List<String> pErrors = errors.get(property);
		if(pErrors == null) {
			pErrors = new ArrayList<>();
		}
			
		pErrors.add(constraint.getMessage());
		errors.put(property, pErrors);
	}
}
