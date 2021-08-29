package com.bookstore.query.mysql8;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import com.bookstore.model.Category;
import com.bookstore.repository.CategoryRepository;

public class CategoryHierarhyQuery {
	
	private String categoryName;
	
	private CategoryRepository repository;
	
	private EntityManager entityManager;

	public CategoryHierarhyQuery(String categoryName, CategoryRepository repository, EntityManager entityManager) {
		super();
		this.categoryName = categoryName;
		this.repository = repository;
		this.entityManager = entityManager;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Category> call() {
		return entityManager.createNativeQuery(query(), Category.class).getResultList();
	}
	
	private List<Category> categoriesScope() {
		return repository.named(categoryName);
	}
	

	private String query() {
		return childrenHierarhyRecursiveCTEs() +
				"SELECT  * FROM " +
				cteQueries().stream()
							.map(cte -> cte.getCTEName())
							.collect(Collectors.joining(" JOIN "));
	}

	private String childrenHierarhyRecursiveCTEs() {
        return "WITH RECURSIVE " +
        		cteQueries().stream()
        					.map(cte -> cte.getCTE())
        					.collect(Collectors.joining(", "));
	}


	private List<HierarhyQuery> cteQueries() {
		return categoriesScope().stream()
								.map(category -> category.getId())
								.map(HierarhyQuery::new)
								.collect(Collectors.toList());
	}

	private class HierarhyQuery {
		private Long categoryId;

		public HierarhyQuery(Long categoryId) {
			super();
			this.categoryId = categoryId;
		}

		public String getCTEName() {
			return "parents" + this.categoryId;
		}

		public String getCTE() {
			String cte = this.getCTEName();

			return cte + " AS (" +
		    		   "  SELECT id, name, parent_id from categories WHERE id = " + this.categoryId +
		    		   "  UNION ALL" +
		    		   "  SELECT c.id, c.name, c.parent_id FROM categories c JOIN " + cte +
		    		   "  ON " + cte + ".id = c.parent_id" +
		    		   ")";
		}
	}

}
