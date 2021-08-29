package com.bookstore.repository;

import java.util.List;

import com.bookstore.model.Book;
import com.bookstore.model.Category;

import com.bookstore.query.mysql8.CategoryHierarhyQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


public class Mysql8BookRepositoryImpl implements Mysql8BookRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
 	private CategoryRepository categoryRepository;
    
    @SuppressWarnings("unchecked")
	@Override
    @Transactional(readOnly = true)
    public Iterable<Book> search(String qk, String q) throws NoSuchFieldException {
    	String column = Book.class.getDeclaredField(qk).getName();

    	return entityManager.createNativeQuery("SELECT * FROM books WHERE MATCH ("+ column +") AGAINST (:q)", Book.class)
    						.setParameter("q", q)
    						.getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
    @Transactional(readOnly = true)
    public Iterable<Book> categorized(String categoryName) {
        List<Category> categories = new CategoryHierarhyQuery(categoryName, categoryRepository, entityManager).call();

        return entityManager.createNativeQuery("SELECT * FROM books WHERE category_id IN (:categories)", Book.class)
				.setParameter("categories", categories)
				.getResultList();
    }
}
