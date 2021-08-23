package com.bookstore.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;

@RestController
public class BookController {
	 @Autowired
	 private BookRepository repository;

	@GetMapping("/books")
	public Iterable<BookDecorator> index(@RequestParam Optional<String> category) {
		Iterable<Book> books;
		
		if (category.isPresent()) {
			books = repository.categorized(category.get());
		} else {
			books = repository.findAll();
		}
		
		return BookDecorator.list(books);
	}

	@GetMapping("/books/{id}")
	public BookDecorator show(@PathVariable Long id) {
		Optional<Book> book = repository.findById(id);
		if (book.isPresent())
			return new BookDecorator(book.get());
		else
			return null;
	}

	@PostMapping("/books")
	public  ResponseEntity<ResponseMessage> create(@RequestBody Book book) {
		repository.save(book);

		return ResponseEntity.ok(new ResponseMessage("Book is successfully created."));
	}

	@PutMapping("/books/{id}")
	public ResponseEntity<ResponseMessage> update(@RequestBody Book book
			, @PathVariable Long id) {

		repository.findById(id)
			      .map(b -> {
			    	  b.setAuthor(book.getAuthor());
			    	  b.setTitle(book.getTitle());
			    	  b.setShort_description(book.getShort_description());
			          return repository.save(b);
			      }).orElseGet(null);


		return ResponseEntity.ok(new ResponseMessage("Book is successfully updated."));
	}

	@DeleteMapping("/books/{id}")
	public ResponseEntity<ResponseMessage> destroy(@PathVariable Long id) {
		repository.deleteById(id);

		return ResponseEntity.ok(new ResponseMessage("Book is successfully removed."));
	}
}
