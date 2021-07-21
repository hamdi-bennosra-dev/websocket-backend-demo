/**
 * 
 */
package com.websocket.demo.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.websocket.demo.utils.FileUploadUtil;

/**
 * @author Hamdi Ben Nosra (hamdi.bennosra.dev@gmail.com)
 *
 */
public class BookDTO {
	private Long id;

	private String title;

	private String cover;

	@JsonIgnoreProperties(value = { "books" }, allowSetters = true)
	private AuthorDTO author;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public AuthorDTO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}
	
	public String getCoverImagePath() {
        if (this.cover == null || this.id == null) return null;
         
        return FileUploadUtil.BOOK_IMG_DIR + id + "/" + this.cover;
    }

}
