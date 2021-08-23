package com.bookstore.repository;

import com.bookstore.model.Book;

public interface CategoryBooksRepository {
	public Iterable<Book> categorized(String categoryName);
}
