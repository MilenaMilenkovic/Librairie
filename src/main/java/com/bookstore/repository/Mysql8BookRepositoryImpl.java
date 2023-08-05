package com.bookstore.repository;

import java.util.ArrayList;
import java.util.List;

import com.bookstore.model.Book;
import com.bookstore.model.Category;

import com.bookstore.query.mysql8.CategoryHierarhyQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


public class Mysql8BookRepositoryImpl implements Mysql8BookRepository {
	private final Pageable defaultPageRequest = PageRequest.of(0, BookRepository.PAGE_SIZE);
	
    @Autowired
    private EntityManager entityManager;

    @Autowired
 	private CategoryRepository categoryRepository;
    
	@Override
    @Transactional(readOnly = true)
    public Iterable<Book> search(String qk, String q) throws NoSuchFieldException {		
    	return search(qk, q, defaultPageRequest);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    @Transactional(readOnly = true)
	public Iterable<Book> search(String qk, String q, Pageable pageable) throws NoSuchFieldException {
		String column = Book.class.getDeclaredField(qk).getName();

    	return entityManager.createNativeQuery(""
    			+ "SELECT * FROM books "
    			+ "WHERE MATCH ("+ column +") AGAINST (:q) LIMIT :offset, :limit", Book.class)
    						.setParameter("q", q)
    						.setParameter("limit", pageable.getPageSize())
    						.setParameter("offset", pageable.getOffset())
    						.getResultList();
	}
    
	@Override
    @Transactional(readOnly = true)
    public Iterable<Book> categorized(String categoryName) {
    	return categorized(categoryName, defaultPageRequest);
    }

	@SuppressWarnings("unchecked")
	@Override
    @Transactional(readOnly = true)
	public Iterable<Book> categorized(String categoryName, Pageable pageable) {    	
		List<Category> categories = new CategoryHierarhyQuery(
				categoryName, categoryRepository, entityManager).call();
		
		if (categories.isEmpty()) return new ArrayList<Book>();

        return entityManager.createNativeQuery("SELECT * FROM books "
        		+ "WHERE category_id IN (:categories) "
        		+ "LIMIT :offset, :limit", Book.class)
				.setParameter("categories", categories)
				.setParameter("limit", pageable.getPageSize())
				.setParameter("offset", pageable.getOffset())
				.getResultList();
	}
}
