package com.bookstore.controller.exception.handlers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.http.HttpStatus;

import com.bookstore.ResponseMessage;
import com.bookstore.controller.exception.RecordNotFoundException;

@ControllerAdvice
public class RecordNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(RecordNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	ResponseMessage RecordNotFoundAdvice(RecordNotFoundException ex) {
		return new ResponseMessage(ex.getMessage());
	}
}
