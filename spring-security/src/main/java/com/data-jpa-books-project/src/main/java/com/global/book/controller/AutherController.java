package com.global.book.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.global.book.dto.AutherDto;
import com.global.book.entity.Auther;
import com.global.book.entity.AutherSearch;
import com.global.book.entity.Book;
import com.global.book.mapper.AutherMapper;
import com.global.book.repository.BookRepo;
import com.global.book.service.AutherService;
import com.global.book.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Tag(name = "Auther Controller")
@Validated
@RestController
@RequestMapping("/auther")
@RequiredArgsConstructor
@CrossOrigin(value = {"http://localhost:4200"})
public class AutherController {

	private final AutherService autherService;
	private final AutherMapper autherMapper;

	@Operation(summary = "Get a book by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the book", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Book not found", content = @Content) })
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(
			@Parameter(example = "20", name = "Book Id") @PathVariable @Min(value = 1) @Max(value = 200) Long id) {

		Auther auther = autherService.findById(id);

		AutherDto dto = autherMapper.map(auther);

		return ResponseEntity.ok(dto);
	}

	@Operation(summary = "Get a book by its email")
	@GetMapping("/email/{email}")
	public ResponseEntity<?> findByEmail(@PathVariable String email) {

		Auther auther = autherService.findByEmail(email).get();

		AutherDto dto = autherMapper.map(auther);

		return ResponseEntity.ok(dto);
	}

	@Operation(summary = "Get all books")
	@GetMapping()
	public ResponseEntity<?> findAll() {
		
		List<Auther> authers = autherService.findAll();
		
		List<AutherDto> dtos = autherMapper.map(authers);

		return ResponseEntity.ok(dtos);
	}

	@Operation(summary = "Add book")
	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody @Valid AutherDto dto) {

		Auther auther = autherMapper.unMap(dto);

		Auther entity = autherService.insert(auther);

		AutherDto returnDto = autherMapper.map(entity);

		return ResponseEntity.ok(returnDto);
	}

	@Operation(summary = "update book")
	@PutMapping("")
	public ResponseEntity<?> update(@RequestBody @Valid AutherDto dto) {
		
		Auther entity = autherService.findById(dto.getId());
		
		Auther auther = autherMapper.unMap(dto, entity);

		Auther returnEntity = autherService.update(auther);

		AutherDto returnDto = autherMapper.map(returnEntity);

		return ResponseEntity.ok(returnDto);
	}

	@Operation(summary = "delete a book by its id")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		autherService.deleteById(id);
		return ResponseEntity.ok(null);
	}

	@Operation(summary = "Get a book by search ")
	@PostMapping("/spec")
	public ResponseEntity<?> findByAutherSpec(@RequestBody AutherSearch search) {

		return ResponseEntity.ok(autherService.findByAutherSpec(search));
	}
}
