package com.bookstore.repository;

import org.springframework.data.domain.Pageable;

import com.bookstore.model.Book;

public interface Mysql8BookRepository {
	public Iterable<Book> search(String qk, String q) throws NoSuchFieldException;
	public Iterable<Book> search(String qk, String q, Pageable pageable) throws NoSuchFieldException;
	public Iterable<Book> categorized(String categoryName);
	public Iterable<Book> categorized(String categoryName, Pageable pageable);
}
