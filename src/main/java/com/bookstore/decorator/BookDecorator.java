package com.bookstore.decorator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.bookstore.model.Book;

public class BookDecorator {
	private Book subject;
	
	// TODO: Make this code reusable
	public static List<BookDecorator> list(Iterable<Book> books) {
		return StreamSupport.stream(books.spliterator(), false)
							.map(BookDecorator::new)
							.collect(Collectors.toList());
	}

	public BookDecorator(Book subject) {
		super();
		this.subject = subject;
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
		// TODO
		return null;
	}
	
	public CategoryDecorator getCategory() {
		return new CategoryDecorator(subject.getCategory());
	}
}
