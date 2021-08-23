package com.bookstore.repository;

import java.util.List;

import com.bookstore.model.Book;
import com.bookstore.model.Category;

import com.bookstore.query.mysql8.CategoryBooksQuery;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;


public class CategoryBooksRepositoryImpl implements CategoryBooksRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
 	private CategoryRepository categoryRepository;

    @Override
    public Iterable<Book> categorized(String categoryName) {
        List<Category> categories = categoryRepository.named(categoryName);

        return CategoryBooksQuery.call(entityManager, categories);
    }
}
