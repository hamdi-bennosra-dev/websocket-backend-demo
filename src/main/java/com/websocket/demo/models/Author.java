/**
 * 
 */
package com.websocket.demo.models;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.websocket.demo.utils.FileUploadUtil;

/**
 * @author hamdi
 *
 */
@Entity
@Table(name = "author")
public class Author implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8107939676315413130L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "surname")
	private String surname;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "portrait")
	private String portrait;

	@OneToMany(mappedBy = "author", orphanRemoval = true, cascade = CascadeType.ALL)
	private Set<Book> books;

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

	public Long getId() {
		return id;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}
	
	@Transient
    public String getPortraitImagePath() {
        if (this.portrait == null || this.id == null) return null;
         
        return FileUploadUtil.AUTHOR_IMG_DIR + id + "/" + this.portrait;
    }
}
