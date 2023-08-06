package com.bookstore.repository.mysql8;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.model.Category;

public interface CategoryHierarhyQueryRepository extends CrudRepository<Category, Long> {
	public List<Category> call();
}
