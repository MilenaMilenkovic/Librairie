package com.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bookstore.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>, PagingAndSortingRepository<Book, Long>, Mysql8BookRepository {
	public static final Integer PAGE_SIZE = 50;
}
