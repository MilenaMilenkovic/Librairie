package com.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bookstore.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>, Mysql8BookRepository {

}
