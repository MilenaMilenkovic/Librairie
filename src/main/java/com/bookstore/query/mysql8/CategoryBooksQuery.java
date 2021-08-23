package com.bookstore.query.mysql8;

import java.util.List;
import java.util.stream.Collectors;

import com.bookstore.model.Book;
import com.bookstore.model.Category;

import javax.persistence.EntityManager;

public class CategoryBooksQuery {
	
	private List<Category> categories;
	
	public CategoryBooksQuery(List<Category> categories) {
		super();
		this.categories = categories;
	}
	
	public static List<Book> call(EntityManager entityManager, List<Category> categories) {
		CategoryBooksQuery queryObject = new CategoryBooksQuery(categories);
		
		return entityManager.createNativeQuery(queryObject.query(), Book.class).getResultList();
	}

	private String query() {
		return childrenHierarhyRecursiveCTEs() +
			   " SELECT * from books where category_id in (select id from " +
				 cteQueries().stream()
				  		     .map(cte -> cte.getCTEName())
				             .collect(Collectors.joining(" JOIN ")) + ")";
	}
	
	private String childrenHierarhyRecursiveCTEs() {         
        return "WITH RECURSIVE " + 
        		cteQueries().stream()
        				    .map(cte -> cte.getCTE())
        					.collect(Collectors.joining(", "));
    }
	
	
	private List<CategoryHierarhyQuery> cteQueries() {
		return categories.stream()
				         .map(category -> category.getId())
				         .map(CategoryHierarhyQuery::new)
				         .collect(Collectors.toList()); 
	}
	
	private class CategoryHierarhyQuery {
		private Long categoryId;

		public CategoryHierarhyQuery(Long categoryId) {
			super();
			this.categoryId = categoryId;
		}
		
		public String getCTEName() {
			return "parents" + this.categoryId;
		}
		
		public String getCTE() {
			String cte = this.getCTEName();
			
			return cte + " AS (" +
		    		   "  SELECT id from categories WHERE id = " + this.categoryId + 
		    		   "  UNION ALL" +
		    		   "  SELECT c.id FROM categories c JOIN " + cte +
		    		   "  ON " + cte + ".id = c.parent_id" +
		    		   ")";
		}
	}
}
