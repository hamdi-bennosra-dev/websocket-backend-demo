/**
 * 
 */
package com.websocket.demo.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.websocket.demo.models.Book;

/**
 * @author hamdi
 *
 */
@Repository
public interface IBookRepository extends CrudRepository<Book, Long> {
	public List<Book> findByAuthorId(Long authorId);
}
