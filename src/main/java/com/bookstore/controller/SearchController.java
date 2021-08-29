package com.bookstore.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.ResponseMessage;
import com.bookstore.decorator.BookDecorator;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;

@RestController
public class SearchController {
	@Autowired
	private BookRepository repository;
	
	@GetMapping("/books/search")
	public ResponseEntity<Object> books(@RequestParam String qk, @RequestParam String q) {
		Iterable<Book> books;
		
		try {
			books = repository.search(qk, q);
		} catch (NoSuchFieldException e) {
			return ResponseEntity.badRequest().body(new ResponseMessage("Invalid search parameters."));
		}
		
		return ResponseEntity.ok(BookDecorator.list(books));
	}

}
