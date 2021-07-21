/**
 * 
 */
package com.websocket.demo.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.websocket.demo.dtos.BookDTO;
import com.websocket.demo.models.Book;
import com.websocket.demo.repositories.IBookRepository;
import com.websocket.demo.utils.FileUploadUtil;

/**
 * @author Hamdi Ben Nosra
 *
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
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

		/*
		 * Persist the book:
		 */
		persistentBook = bookRepository.save(persistentBook);

		/*
		 * Return the persisted author:
		 */
		return convertToDto(persistentBook);
	}

	@PostMapping("/upload-image")
	@ResponseBody
	public BookDTO uploadImage(@RequestParam("id") Long id, @RequestParam("cover") MultipartFile multipartFile)
			throws IOException {
		Optional<Book> reponse = bookRepository.findById(id);

		if (reponse.isPresent()) {
			Book persistentBook = reponse.get();

			/*
			 * Attach the uploaded image to the book:
			 */
			String fileName = multipartFile.getOriginalFilename();
			if (fileName != null) {
				persistentBook.setCover(StringUtils.cleanPath(fileName));
			}

			/*
			 * Update the book with the cover path:
			 */
			persistentBook = bookRepository.save(persistentBook);

			/*
			 * Save the uploaded image on the server:
			 */
			String uploadDir = FileUploadUtil.BOOK_IMG_DIR + persistentBook.getId();
			FileUploadUtil.saveFile(uploadDir, persistentBook.getCover(), multipartFile);

			/*
			 * Return the book:
			 */
			return convertToDto(persistentBook);
		}

		return null;
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

	@MessageMapping("/book-added")
	@SendTo("/books/channel")
	public BookDTO bookAdded(String id) throws InterruptedException {
		Thread.sleep(1000); // simulated delay
		Optional<Book> response = bookRepository.findById(Long.valueOf(id));

		if (response.isPresent()) {
			return convertToDto(response.get());
		}

		return null;
	}

	/*
	 * Utils
	 */
	private Book convertToEntity(BookDTO bookDto) {
		return modelMapper.map(bookDto, Book.class);
	}

	private BookDTO convertToDto(Book book) {
		return modelMapper.map(book, BookDTO.class);
	}
}
