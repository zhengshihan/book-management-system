package com.bookstore.controller;

import java.util.List;
import org.springframework.ui.Model;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@Controller
public class BookController {
	
	@Autowired
	private BookService bookService;

	@GetMapping("/bookstore/create")
	public String showCreateBookForm(Model model) {
	    Book book = new Book(); // 创建一个空的 Book 对象
	    model.addAttribute("book", book);
	    return "create_book"; // 返回创建书籍的视图
	}

	
	
	
	@GetMapping("/bookstore/{author}/books")
	public String getAllBooks(@PathVariable String author,Model model){
		List<Book> books = bookService.getAllBooksByAuthor(author);
		model.addAttribute("books", books);
        return "book_search";
//		return books;
	}
	 @GetMapping("/bookstore/{author}/books/after/{year}")
	    public String getBooksByAuthorAfterYear(@PathVariable String author, @PathVariable int year, Model model) {
	        List<Book> books = bookService.getBooksByAuthorAfterYear(author, year);
	        System.out.print(books);
	        model.addAttribute("books", books);
	        model.addAttribute("author", author);
	        return "book_search"; 
	    }
//	@GetMapping("/bookstore/books")
//	public String getAllBooks(Model model){
//		List<Book> books = bookService.getAllBooks();
//		model.addAttribute("books", books);
//		System.out.print(books);
//        return "book_list";
////		return books;
//	}
//	
	 @GetMapping("/bookstore/books")
	    public String getAllBooks(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "5") int size,
	            Model model) {
	        Pageable pageable = PageRequest.of(page, size);
	        Page<Book> booksPage = bookService.getAllBooks(pageable);
	        model.addAttribute("books", booksPage.getContent());
	        System.out.print(booksPage);
	        model.addAttribute("currentPage", page);
	        model.addAttribute("totalPages", booksPage.getTotalPages());
	        return "book_list"; // 返回视图
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
	
	
	
	
//	@PostMapping("/bookstore/{author}/books")
//	public ResponseEntity<Void>createLesson(@PathVariable String author, @RequestBody Book book  ){
//		book.setAuthor(author);
//		Book createdBook  = bookService.createBook(author, book);
//		if(createdBook == null)
//			return ResponseEntity.noContent().build();
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdBook.getId()).toUri();
//		return ResponseEntity.created(uri).build();
//	    
//		
//	}
//	@PostMapping("/bookstore/{author}/books")
//	public ResponseEntity<Void>createBook(@PathVariable String author, @RequestBody Book book  ){
//		book.setAuthor(author);
//		Book createdBook  = bookService.createBook(author, book);
//		if(createdBook == null)
//			return ResponseEntity.noContent().build();
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdBook.getId()).toUri();
//		return ResponseEntity.created(uri).build();
//	    
//		
//	}

	
	@PostMapping("/bookstore/books")
	public ResponseEntity<Void> createBook(@ModelAttribute Book book) {
	    Book createdBook = bookService.createBook(book.getAuthor(), book);
	    if (createdBook == null) {
	        return ResponseEntity.noContent().build();
	    }
	    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdBook.getId()).toUri();
	    return ResponseEntity.created(uri).build();
	}

}
