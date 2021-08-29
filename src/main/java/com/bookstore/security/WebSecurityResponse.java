package com.bookstore.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.bookstore.ResponseMessage;

public abstract class WebSecurityResponse {

	public static AuthenticationEntryPoint authenticationEntryPoint() {
		return (request, response, e) -> {
	        response.setContentType("application/json;charset=UTF-8");
	        response.setStatus(HttpStatus.UNAUTHORIZED.value());
	        response.getWriter().write(ResponseMessage.json("User is not authorized."));
	    };
	}
	
	public static AuthenticationSuccessHandler authenticationSuccess() {
		return (request, response, e) -> {
			response.setContentType("application/json;charset=UTF-8");
	        response.setStatus(HttpStatus.OK.value());
	        response.getWriter().write(ResponseMessage.json("User is successfully logged in."));
		};
	}
	
	public static AuthenticationFailureHandler authenticationFailed() {
		return (request, response, e) -> {
			response.setContentType("application/json;charset=UTF-8");
	        response.setStatus(HttpStatus.BAD_REQUEST.value());
	        response.getWriter().write(ResponseMessage.json("Invalid username or password."));
		};
	}
	
	public static LogoutSuccessHandler logoutHandler() {
		return (request, response, e) -> {
			response.setContentType("application/json;charset=UTF-8");
	        response.setStatus(HttpStatus.OK.value());
	        response.getWriter().write(ResponseMessage.json("User is successfully logged out."));
		};
	}
}
