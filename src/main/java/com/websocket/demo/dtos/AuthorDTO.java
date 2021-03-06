/**
 * 
 */
package com.websocket.demo.dtos;

import java.util.Set;

import com.websocket.demo.utils.FileUploadUtil;

/**
 * @author Hamdi Ben Nosra (hamdi.bennosra.dev@gmail.com)
 *
 */
public class AuthorDTO {
	private Long id;

	private String name;

	private String surname;

	private String phoneNumber;

	private String portrait;

	private Set<BookDTO> books;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<BookDTO> getBooks() {
		return books;
	}

	public void setBooks(Set<BookDTO> books) {
		this.books = books;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

    public String getPortraitImagePath() {
        if (this.portrait == null || this.id == null) return null;
         
        return FileUploadUtil.AUTHOR_IMG_DIR + id + "/" + this.portrait;
    }
}
