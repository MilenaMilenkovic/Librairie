package com.bookstore.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.ResponseMessage;
import com.bookstore.decorator.BookDecorator;
import com.bookstore.interactor.BookInteractor;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;

@RestController
public class BookController {
	@Autowired
	private BookRepository repository;
	 
	@Autowired
	private BookInteractor interactor;

	@GetMapping("/books")
	public ResponseEntity<Object> index(@RequestParam Optional<String> category) {
		Iterable<Book> books;
		
		if (category.isPresent()) {
			books = repository.categorized(category.get());
		} else {
			books = repository.findAll();
		}
		
		return ResponseEntity.ok(BookDecorator.list(books));
	}

	@GetMapping("/books/{id}")
	public ResponseEntity<Object> show(@PathVariable Long id) {
		Optional<Book> book = repository.findById(id);
		
		if (book.isPresent()) {
			return ResponseEntity.ok(new BookDecorator(book.get()));
		} else {
			return new ResponseEntity<>(ResponseMessage.notFound(), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/books")
	public  ResponseEntity<Object> create(@RequestBody Book book) {
		boolean created = interactor.create(book);

		if (created) {
			return ResponseEntity.ok(new ResponseMessage("Book is successfully created."));
		} else {
			return ResponseEntity.badRequest().body(interactor.getErrors());
		}
	}

	@PutMapping("/books/{id}")
	public ResponseEntity<Object> update(@RequestBody Book book
			, @PathVariable Long id) {
		
		Optional<Book> b = repository.findById(id);
		
		if (b.isPresent()) {
			boolean updated = interactor.update(b.get(), book);
			
			if (updated) {
				return ResponseEntity.ok(new ResponseMessage("Book is successfully updated."));
			} else {
				return ResponseEntity.badRequest().body(interactor.getErrors());
			}
		} else {
			return new ResponseEntity<>(ResponseMessage.notFound(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/books/{id}")
	public ResponseEntity<Object> destroy(@PathVariable Long id) {
		repository.deleteById(id);

		return ResponseEntity.ok(new ResponseMessage("Book is successfully removed."));
	}
}
