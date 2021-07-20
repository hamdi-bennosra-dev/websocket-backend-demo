/**
 * 
 */
package com.websocket.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.websocket.demo.dtos.BookDTO;
import com.websocket.demo.models.Book;
import com.websocket.demo.repositories.IBookRepository;

/**
 * @author Hamdi Ben Nosra
 *
 */
@RestController
@Transactional
@RequestMapping("/api/book")
public class BookController {

	@Autowired
	private IBookRepository bookRepository;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public BookDTO postBook(@RequestBody BookDTO bookDto) {
		Book persistentBook = convertToEntity(bookDto);

		persistentBook = bookRepository.save(persistentBook);

		return convertToDto(persistentBook);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BookDTO getBookById(@PathVariable Long id) {
		Optional<Book> reponse = bookRepository.findById(id);

		if (reponse.isPresent()) {
			return convertToDto(reponse.get());
		}

		return null;
	}

	@GetMapping("/author/{authorId}")
	@ResponseBody
	public List<BookDTO> getBooksByAuthor(@PathVariable Long authorId) {
		List<BookDTO> books = new ArrayList<>();

		for (Book book : bookRepository.findByAuthorId(authorId)) {
			books.add(convertToDto(book));
		}

		return books;
	}

	@GetMapping
	@ResponseBody
	public List<BookDTO> getBooks() {  
		List<BookDTO> books = new ArrayList<>();

		for (Book book : bookRepository.findAll()) {
			books.add(convertToDto(book));
		}

		return books;
	}

	private Book convertToEntity(BookDTO bookDto) {
		return modelMapper.map(bookDto, Book.class);
	}

	private BookDTO convertToDto(Book book) {
		return modelMapper.map(book, BookDTO.class);
	}
}
