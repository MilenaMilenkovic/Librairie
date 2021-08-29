package com.bookstore.repository;

import com.bookstore.model.Book;

public interface Mysql8BookRepository {
	public Iterable<Book> search(String qk, String q) throws NoSuchFieldException;
	public Iterable<Book> categorized(String categoryName);
}
