package com.bookstore;

import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
	// Takes care on Hibernate session
	private BookRepository repository;
	
	BookController(BookRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping("/books")
	public Iterable<Book> index() {
		return repository.findAll();
	}
	
	@GetMapping("/books/{id}")
	public Optional<Book> show(@PathVariable Long id) {
		return repository.findById(id);
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
