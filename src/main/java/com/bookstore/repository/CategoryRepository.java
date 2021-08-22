package com.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
