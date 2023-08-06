package com.bookstore.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.decorator.BookDecorator;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;

@RestController
public class SearchController {
	@Autowired
	private BookRepository repository;
	
	@GetMapping("/books/search")
	public List<BookDecorator> books(@RequestParam("page") Optional<Integer> page, 
			@RequestParam String qk, @RequestParam String q) {
		Iterable<Book> books;
		
		Pageable paging = PageRequest.of(page.orElse(0), BookRepository.PAGE_SIZE);
			
		switch(qk) {
		case "author":
			books = repository.searchAuthor(q, paging);
			break;
		case "title":
			books = repository.searchTitle(q, paging);
			break;
		case "short_description":
			books = repository.searchShortDescription(q, paging);
			break;
		default:
			return new ArrayList<BookDecorator>();
		}
		
		return BookDecorator.list(books);
	}

}

