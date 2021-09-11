package com.bookstore.decorator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;

public class BookDecorator extends Decorator<Book> {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8892402062629541917L;

	// TODO: Make this code reusable
	public static List<BookDecorator> list(Iterable<Book> books) {
		return StreamSupport.stream(books.spliterator(), false)
							.map(BookDecorator::new)
							.collect(Collectors.toList());
	}

	public BookDecorator(Book subject) {
		super(subject);
	}
	
	public String getTitle() {
		return subject.getTitle();
	}
	
	public String getAuthor() {
		return subject.getAuthor();
	}
	
	public String getShort_description() {
		return subject.getShort_description();
	}
	
	public String getCover_image_url() {	
		return subject.getCover_image_url();
	}
	
	public CategoryDecorator getCategory() {
		return new CategoryDecorator(subject.getCategory());
	}
}
