package com.bookstore.controller;

import java.util.List;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;

import com.bookstore.model.Book;
import com.bookstore.service.BookService;
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
public class BookController {
	
	@Autowired
	private BookService bookService;

	
	@GetMapping("/bookstore/{author}/books")
	public List<Book> getAllBooks(@PathVariable String author){
		List<Book> books = bookService.getAllGroups(author);
		return books;
	}
	@GetMapping("/bookstore/{author}/books/{id}")
	public Book getBook(@PathVariable String author, @PathVariable long id) {
		Book book = bookService.getBook(author, id);
		return book;
	}
	
	@GetMapping("/bookstore/search")
	public List<Book>searchBooks(@RequestParam String author, @RequestParam String title){
		List<Book> books = bookService.searchBookByAuthorAndTitle(author, title);
		return books;
	}
	
	
	
	
	
	
	@DeleteMapping("/bookstore/{author}/books/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable String author, @PathVariable long id){
		bookService.deleteBook(author, id);
		ResponseEntity<Void> responseEntity = ResponseEntity.noContent().build();
		return responseEntity;
	}
	
	@PutMapping("/bookstore/{author}/books/{id}")
	public ResponseEntity<Book>updateBook(@PathVariable String author, @PathVariable long id, @RequestBody Book book){
		book.setAuthor(author);
		Book bookUpdated = bookService.updateBook(author, id, book);
		ResponseEntity<Book> responseEntity = new ResponseEntity<Book>(bookUpdated, HttpStatus.OK);
		return responseEntity;
	}
	
	@PostMapping("/bookstore/{author}/books")
	public ResponseEntity<Void>createLesson(@PathVariable String author, @RequestBody Book book  ){
		book.setAuthor(author);
		Book createdBook  = bookService.createBook(author, book);
		if(createdBook == null)
			return ResponseEntity.noContent().build();
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdBook.getId()).toUri();
		return ResponseEntity.created(uri).build();
	    
		
	}

}
