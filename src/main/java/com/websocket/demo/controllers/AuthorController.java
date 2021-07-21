/**
 * 
 */
package com.websocket.demo.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
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

import com.websocket.demo.dtos.AuthorDTO;
import com.websocket.demo.models.Author;
import com.websocket.demo.models.Book;
import com.websocket.demo.repositories.IAuthorRepository;
import com.websocket.demo.utils.FileUploadUtil;

/**
 * @author Hamdi Ben Nosra
 *
 */
@RestController
@RequestMapping("/api/author")
public class AuthorController {

	@Autowired
	private IAuthorRepository authorRepository;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public AuthorDTO postAuthor(@RequestBody AuthorDTO authorDto, @RequestParam("portrait") MultipartFile multipartFile)
			throws IOException {
		Author persistentAuthor = convertToEntity(authorDto);

		/*
		 * Link the books to the author (bidirectional relation):
		 */
		for (Book book : persistentAuthor.getBooks()) {
			book.setAuthor(persistentAuthor);
		}

		/*
		 * Attach the uploaded image to the author:
		 */
		String fileName = multipartFile.getOriginalFilename();
		if (fileName != null) {
			persistentAuthor.setPortrait(StringUtils.cleanPath(fileName));
		}

		/*
		 * Persist the author:
		 */
		persistentAuthor = authorRepository.save(persistentAuthor);

		/*
		 * Save the uploaded image on the server:
		 */
		String uploadDir = FileUploadUtil.AUTHOR_IMG_DIR + persistentAuthor.getId();
		FileUploadUtil.saveFile(uploadDir, persistentAuthor.getPortrait(), multipartFile);

		/*
		 * Return the persisted author:
		 */
		return convertToDto(persistentAuthor);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public AuthorDTO getAuthor(@PathVariable Long id) {
		Optional<Author> reponse = authorRepository.findById(id);

		if (reponse.isPresent()) {
			return convertToDto(reponse.get());
		}

		return null;
	}

	@GetMapping
	@ResponseBody
	public List<AuthorDTO> getAuthors() {
		List<AuthorDTO> authors = new ArrayList<>();

		for (Author author : authorRepository.findAll()) {
			authors.add(convertToDto(author));
		}

		return authors;
	}

	private Author convertToEntity(AuthorDTO authorDto) {
		return modelMapper.map(authorDto, Author.class);
	}

	private AuthorDTO convertToDto(Author author) {
		return modelMapper.map(author, AuthorDTO.class);
	}
}
