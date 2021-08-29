package com.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.controller.exception.InvalidBookSearchException;
import com.bookstore.decorator.BookDecorator;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;

@RestController
public class SearchController {
	@Autowired
	private BookRepository repository;
	
	@GetMapping("/books/search")
	public List<BookDecorator> books(@RequestParam String qk, @RequestParam String q) {
		Iterable<Book> books;
		
		try {
			books = repository.search(qk, q);
		} catch (NoSuchFieldException e) {
			throw new InvalidBookSearchException();
		}
		
		return BookDecorator.list(books);
	}

}
