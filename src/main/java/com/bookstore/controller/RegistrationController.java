package com.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.ResponseMessage;
import com.bookstore.interactor.UserInteractor;
import com.bookstore.model.User;

@RestController
public class RegistrationController {
	
	@Autowired
	private UserInteractor interactor;
	
	@PostMapping("/registration")
	public  ResponseEntity<Object> create(@RequestBody User user) {
		boolean created = interactor.create(user);

		if (created) {
			return ResponseEntity.ok(new ResponseMessage("User is successfully created."));
		} else {
			return ResponseEntity.badRequest().body(interactor.getErrors());
		}
	}

}
