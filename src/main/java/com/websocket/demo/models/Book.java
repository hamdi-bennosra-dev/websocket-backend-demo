/**
 * 
 */
package com.websocket.demo.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.websocket.demo.utils.FileUploadUtil;

/**
 * @author hamdi
 *
 */
@Entity
@Table(name = "book")
public class Book implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3609294731451014595L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title")
	private String title;

    @Column(name="cover")
    private String cover;
	
	@ManyToOne
	@JoinColumn(name = "author_fk")
	private Author author;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Long getId() {
		return id;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}
	
	@Transient
    public String getCoverImagePath() {
        if (this.cover == null || this.id == null) return null;
         
        return FileUploadUtil.BOOK_IMG_DIR + id + "/" + this.cover;
    }
	
}
