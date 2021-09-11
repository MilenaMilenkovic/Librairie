package com.bookstore.controller.exception.handlers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bookstore.ResponseMessage;

@ControllerAdvice
public class IOExceptionAdvice {

	@ResponseBody
	@ExceptionHandler(IOException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ResponseMessage IOExceptionAdvice(IOException ex) {
		return new ResponseMessage(ex.getMessage());
	}
}
