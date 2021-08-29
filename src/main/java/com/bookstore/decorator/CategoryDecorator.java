package com.bookstore.decorator;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import com.bookstore.model.Category;

public class CategoryDecorator extends Decorator<Category> {
	
	public CategoryDecorator(Category subject) {
		super(subject);
	}

	public String getName() {
		return subject.getName();
	}
	
	public Map<String, String> getParent() {
		Category p = subject.getParent();
		if (p == null) return null;
		
		return Map.of("id", p.getId().toString(), "name", p.getName());
	}
	
	public List<String> getParents() {
		ArrayList<String> parents = new ArrayList<>();
		Category parent = subject.getParent();
				
		while(parent != null) {
			parents.add(parent.getName());
			parent = parent.getParent();
		}
		
		return parents;
	}
}
