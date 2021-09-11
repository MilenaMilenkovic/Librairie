package com.bookstore.interactor;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.model.Book;
import com.bookstore.model.Category;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CategoryRepository;
import com.bookstore.storage.StorageService;


@Service
public class BookInteractor extends Interactor<Book, BookRepository>{
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	StorageService storageService;
		
	public boolean create(Long categoryId, Book book, 
			Optional<MultipartFile> coverImage) throws IOException {
		
  	  	subject = new Book();
  	  	
  	  	Category category = categoryRepository.findById(categoryId).orElse(null);
  	  	String coverImageData = uploadCoverImage(coverImage.orElse(null));
  	  	
  	  	subject.setCategory(category);
		subject.setAuthor(book.getAuthor());
		subject.setTitle(book.getTitle());
  	  	subject.setShort_description(book.getShort_description());
  	  	subject.setCover_image_data(coverImageData);
		
  	  	return this.save();
	}	
	
	public boolean update(Book book, Book update, 
			Optional<Long> categoryId, Optional<MultipartFile> coverImage) throws IOException {
		subject = book;
		
		if (categoryId.isPresent()) {
			Category category = categoryRepository.findById(categoryId.get()).orElse(null);
			subject.setCategory(category);
		}
		
		if (update.getAuthor() != null)
			subject.setAuthor(update.getAuthor());
		
		if (update.getTitle() != null)
			subject.setTitle(update.getTitle());
		
		if (update.getShort_description() != null)
  	  		subject.setShort_description(update.getShort_description());
		
		if (coverImage.isPresent()) {
			String data = uploadCoverImage(subject, coverImage.get());
			subject.setCover_image_data(data);
		}
		
		return this.save();
	}
	
	private String uploadCoverImage(Book book, MultipartFile coverImage) throws IOException {
		// Replace cover image
		String imageData = uploadCoverImage(coverImage);
		
		storageService.delete(book.getCover_image_url());
		
		return imageData;
	}
	
	private String uploadCoverImage(MultipartFile coverImage) throws IOException {
		if (coverImage == null) return null;
		
		return storageService.store(coverImage);
	}
}
