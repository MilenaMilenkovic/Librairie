package com.bookstore.interactor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.bookstore.model.Book;
import com.bookstore.model.Category;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CategoryRepository;


// TODO: Find out less generic annotation
@Component 
public class BookInteractor {
	@Autowired
	private BookRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	private Book subject;
	
	public boolean create(Book book) {
  	  	subject = new Book();
  	  	
  	  	subject.setCategory(categoryOf(book));
		subject.setAuthor(book.getAuthor());
		subject.setTitle(book.getTitle());
  	  	subject.setShort_description(book.getShort_description());
		
		return save();
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
		
		return save();
	}
	
	public Map<String, List<String>> getErrors() {
		return subject.getErrors();
	}
	
	private boolean save() {  	  
  	  	if (!subject.isValid())
  	  		return false;
  	  	
  	  	repository.save(subject);
  		return true;
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
