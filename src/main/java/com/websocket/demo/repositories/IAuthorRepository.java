/**
 * 
 */
package com.websocket.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.websocket.demo.models.Author;

/**
 * @author hamdi
 *
 */
@Repository
public interface IAuthorRepository extends CrudRepository<Author, Long> {

}
