package com.bookstore.decorator;

import java.io.Serializable;

public abstract class Decorator<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5858028065121234883L;
	
	protected T subject;
	
	protected Decorator(T subject) {
		super();
		this.subject = subject;
	}
}
