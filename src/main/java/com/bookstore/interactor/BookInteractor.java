package com.bookstore.interactor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.model.Book;
import com.bookstore.model.Category;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CategoryRepository;


@Service
public class BookInteractor extends Interactor<Book, BookRepository>{
	@Autowired
	private CategoryRepository categoryRepository;
		
	public boolean create(Book book) {
  	  	subject = new Book();
  	  	subject.setCategory(categoryOf(book));
		subject.setAuthor(book.getAuthor());
		subject.setTitle(book.getTitle());
  	  	subject.setShort_description(book.getShort_description());
		
  	  	return this.save();
	}	
	
	public boolean update(Book book, Book update) {
		subject = book;
		
		if (update.getCategory() != null)
			subject.setCategory(categoryOf(update));
		
		if (update.getAuthor() != null)
			subject.setAuthor(update.getAuthor());
		
		if (update.getTitle() != null)
			subject.setTitle(update.getTitle());
		
		if (update.getShort_description() != null)
  	  		subject.setShort_description(update.getShort_description());
		
		return this.save();
	}
	
	private Category categoryOf(Book book) {
		Category category = null;
  	  	if (book.getCategory() != null) {
  	  		Long categoryId = book.getCategory().getId();
  	  		category = categoryRepository.findById(categoryId).orElse(null);
  	  	}
  	  	return category;
	}
}
