package com.bookstore.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.bookstore.model.Book;
import com.bookstore.model.Category;

public interface BookRepository extends JpaRepository<Book, Long>, 
	PagingAndSortingRepository<Book, Long>, Mysql8BookRepository {
	
	public static final Integer PAGE_SIZE = 50;
		
	@Query("select b from Book b where category_id in (:categories)")
	public List<Book> categorized(@Param("categories") List<Category> categories, Pageable pageable);
}