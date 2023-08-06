package com.bookstore.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookstore.model.Book;
import com.bookstore.model.Category;

public interface Mysql8BookRepository {
	// Temporary implementation
	@Query(value = "select b from Book b where author MATCH (:author)", nativeQuery = true)
	public List<Book> search(@Param("author") String author, Pageable pageable);
}
