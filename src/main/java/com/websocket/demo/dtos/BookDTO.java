/**
 * 
 */
package com.websocket.demo.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author hamdi
 *
 */
public class BookDTO {
	private Long id;

	private String title;

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

	public AuthorDTO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}

}
