package com.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.ResponseMessage;
import com.bookstore.controller.exception.EntityValidationException;
import com.bookstore.interactor.UserInteractor;
import com.bookstore.model.User;

@RestController
public class RegistrationController {
	
	@Autowired
	private UserInteractor interactor;
	
	@PostMapping("/registration")
	public  ResponseEntity<Object> create(@RequestBody User user) {
		if (!interactor.create(user))
			throw new EntityValidationException(interactor.getErrors());
		
		return ResponseEntity.ok(new ResponseMessage(("User is successfully created.")));
	}

}
