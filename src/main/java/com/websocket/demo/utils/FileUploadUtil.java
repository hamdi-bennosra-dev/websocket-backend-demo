/**
 * 
 */
package com.websocket.demo.utils;

import java.io.*;
import java.nio.file.*;

import org.springframework.web.multipart.MultipartFile;

/*
 * @author https://www.codejava.net/nam-ha-minh
 */
public class FileUploadUtil {

	public static final String AUTHOR_IMG_DIR = "authors-portraits/";
	public static final String BOOK_IMG_DIR = "books-covers/";
	
	private FileUploadUtil() {

	}

	public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(uploadDir);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save image file: " + fileName, ioe);
		}
	}
}