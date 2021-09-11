package com.bookstore.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.ResponseMessage;
import com.bookstore.controller.exception.EntityValidationException;
import com.bookstore.controller.exception.RecordNotFoundException;
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
	public List<BookDecorator> index(@RequestParam Optional<String> category) {
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
		Book book = repository.findById(id)
							  .orElseThrow(() -> new RecordNotFoundException());
		
		return new BookDecorator(book);
	}

	@PostMapping(value = "/books", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseMessage create(Book book, Long category_id,
			@RequestParam("cover_image") Optional<MultipartFile> coverImage) throws IOException {
				
		if (!interactor.create(category_id, book, coverImage))
			throw new EntityValidationException(interactor.getErrors());
		
		return new ResponseMessage("Book is successfully created.");
	}

	@PutMapping(value = "/books/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseMessage update(@PathVariable Long id,
			Book update, Optional<Long> category_id, 
			@RequestParam("cover_image") Optional<MultipartFile> coverImage) throws IOException {
				
		Book book = repository.findById(id)
				  			  .orElseThrow(() -> new RecordNotFoundException());
		
		if (!interactor.update(book, update, category_id, coverImage))
			throw new EntityValidationException(interactor.getErrors());
			
		return new ResponseMessage("Book is successfully updated.");
	}

	@DeleteMapping("/books/{id}")
	public ResponseMessage destroy(@PathVariable Long id) {
		repository.deleteById(id);

		return new ResponseMessage("Book is successfully removed.");
	}
	
	
}
