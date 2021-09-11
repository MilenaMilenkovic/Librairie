package com.bookstore.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {

	void init() throws IOException;
	
	boolean delete(String file) throws IOException;

	/*
	 * Returns metadata (JSON) of uploaded file 
	 */
	String store(MultipartFile file) throws IOException;
}
