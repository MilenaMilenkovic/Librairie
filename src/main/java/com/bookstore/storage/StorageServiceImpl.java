package com.bookstore.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;


@Service
public class StorageServiceImpl implements StorageService {
	private final Path root;
	private final String storage;
	
	@Autowired
	public StorageServiceImpl(StorageProperties properties) {
		this.root = Paths.get(properties.getLocation());
		this.storage = properties.getStorage();
	}
	
	@Override
	public void init() throws IOException {
		if (!Files.exists(root))
			Files.createDirectory(root);
	}

	@Override
	public String store(MultipartFile file) throws IOException {
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		if (!extension.isEmpty())
			extension = "." + extension;
				
		Path path = this.root.resolve(UUID.randomUUID() + extension);
		
		Files.copy(file.getInputStream(), path);
		
		return dataOf(file, path);
	}
	
	@Override
	public boolean delete(String file) throws IOException {
		if (file == null) return true;
		
		File f = new File(root + "/" + file);
		return f.delete();
	}
	
	private String dataOf(MultipartFile file, Path uploadedFile) throws IOException {
		Map<String, Object> data = new HashMap<>();
		
		data.put("id", uploadedFile.getFileName().toString());
		data.put("storage", storage);
		data.put("metadata", metadataOf(file));
		
		return new Gson().toJson(data).toString();
	}
	
	private Map<String, String> metadataOf(MultipartFile file) throws IOException {
		Map<String, String> metadata = new HashMap<>();
		
		metadata.put("filename", file.getOriginalFilename());
		metadata.put("size", String.valueOf(file.getSize()));
		metadata.put("mime_type", file.getContentType());
		
		return metadata;
	}
}
