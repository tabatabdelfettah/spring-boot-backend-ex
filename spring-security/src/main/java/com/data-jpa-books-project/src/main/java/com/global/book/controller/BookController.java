package com.global.book.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.global.book.dto.BookDto;
import com.global.book.entity.Book;
import com.global.book.mapper.BookMapper;
import com.global.book.service.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;
	private final BookMapper bookMapper;

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {

		Book book = bookService.findById(id);

		BookDto dto = bookMapper.map(book);

		return ResponseEntity.ok(dto);
	}

	@GetMapping()
	public ResponseEntity<?> findAll() {

		return ResponseEntity.ok(bookService.findAll());
	}

	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody @Valid BookDto dto) {
		
		Book book = bookMapper.unMap(dto);

		return ResponseEntity.ok(bookService.insert(book));
	}

	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody @Valid Book entity) {

		return ResponseEntity.ok(bookService.update(entity));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		bookService.deleteById(id);
		return ResponseEntity.ok(null);
	}

	@DeleteMapping("/auther/{id}")
	public ResponseEntity<?> deleteByAutherId(@PathVariable Long id) {

		return ResponseEntity.ok(bookService.deleteByAutherId(id));
	}

}
