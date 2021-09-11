package com.bookstore.model;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "books")
public class Book {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String author;
	
	private String title;

	private String cover_image_data;
	
	private String short_description;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	@NotNull(message = "must exist")
	private Category category;
	
	@CreationTimestamp
	private Timestamp created_at;
	
	@UpdateTimestamp
	private Timestamp updated_at;
	
	public Book() {}
	
	public Book(String author, String title, String short_description, Category category) {
		super();
		this.author = author;
		this.title = title;
		this.short_description = short_description;
		this.category = category;
	}

	public Long getId() {
		return id;
	}
	
	@JsonProperty("category_id")
	public void setCategory(Long id) {
		this.category = new Category();
		this.category.setId(id);
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}


	public Category getCategory() {
		return category;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCover_image_url() {
		if (cover_image_data == null)
			return null;
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Type t = new TypeToken<Map<String, Object>>() {}.getType();
		Map<String, String> data = gson.fromJson(cover_image_data, t);
		
		return data.get("id");
	}

	public String getCover_image_data() {
		return cover_image_data;
	}

	public void setCover_image_data(String cover_image_data) {
		this.cover_image_data = cover_image_data;
	}

	public String getShort_description() {
		return short_description;
	}

	public void setShort_description(String short_description) {
		this.short_description = short_description;
	}
	
	public Timestamp getCreated_at() {
		return created_at;
	}

	public Timestamp getUpdated_at() {
		return updated_at;
	}
	
}
