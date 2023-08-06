package com.bookstore.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookstore.model.Book;

public interface Mysql8BookRepository {

	@Query(value = "select * from books where match(author) against(:author)", nativeQuery = true)
	public List<Book> searchAuthor(@Param("author") String author, Pageable pageable);
	
	@Query(value = "select * from books where match(title) against(:title)", nativeQuery = true)
	public List<Book> searchTitle(@Param("title") String title, Pageable pageable);
	
	@Query(value = "select * from books where match(short_description) against(:text)", nativeQuery = true)
	public List<Book> searchShortDescription(@Param("text") String text, Pageable pageable);
}
