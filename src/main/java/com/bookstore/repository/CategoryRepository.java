package com.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

import com.bookstore.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

	@Query("select c from Category c where name = :name")
	public List<Category> named(@Param("name") String name);
}
