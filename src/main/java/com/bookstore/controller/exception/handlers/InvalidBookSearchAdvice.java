package com.bookstore.controller.exception.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bookstore.ResponseMessage;
import com.bookstore.controller.exception.InvalidBookSearchException;

@ControllerAdvice
public class InvalidBookSearchAdvice {

	@ResponseBody
	@ExceptionHandler(InvalidBookSearchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ResponseMessage InvalidBookSearchAdvice(InvalidBookSearchException ex) {
		return new ResponseMessage(ex.getMessage());
	}
}
