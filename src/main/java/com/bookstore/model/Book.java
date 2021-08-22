package com.bookstore.model;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


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

	public String getCover_image_data() {
		return cover_image_data;
	}

	public void setCover_image_data(String cover_image_data) {
		this.cover_image_data = cover_image_data;
	}

	public String getShort_description() {
		return short_description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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
