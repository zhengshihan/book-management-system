package com.bookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bookstore.exception.BookNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import com.bookstore.service.impl.BookServiceImpl;






@ExtendWith(SpringExtension.class)
public class BookServiceMockTest {

	@Mock
	private BookRepository bookRepository;
	
	@InjectMocks
	private BookService bookService = new BookServiceImpl();
	
	@Test
	public void getAllBooks() {
		List<Book>books = Arrays.asList(
				new Book(10001, "bytecaptain", "Learn Java","This is a book"),
				new Book(10002, "bytecaptain", "Learn Spring Boot","This is a book"));
		when(bookRepository.findAll()).thenReturn(books);
		assertEquals(books, bookService.getAllBooks("bytecaptain"));
				
	}
	@Test
    public void getBook() {
    	Book book = new Book(10001, "bytecaptain", "Learn Java","This is a book");
    	
    	when(bookRepository.findById(Long.valueOf(10001))).thenReturn(Optional.of(book));
		assertEquals(book, bookService.getBook("bytecaptain", Long.valueOf(10001)));
    }
    @Test
    public void getBookNotFound() {
    	
    	BookNotFoundException exception = assertThrows(
    			BookNotFoundException.class,
    	           () -> bookService.getBook("bytecaptain", Long.valueOf(10001)),
    	           "Book id not found : 10001"
    	    );

    	    assertEquals("Book id not found : 10001", exception.getMessage());
    }
    @Test
    public void createBook() {
    	Book book = new Book(10001, "bytecaptain", "Learn Java","This is a book");
    	
    	when(bookRepository.save(book)).thenReturn(book);
		assertEquals(book, bookService.createBook("bytecaptain", book));
    }
    @Test
    public void updateBook() {
    	Book book = new Book(10001, "bytecaptain", "Learn Java","This is a book");
    	
    	when(bookRepository.save(book)).thenReturn(book);
		assertEquals(book, bookService.updateBook("bytecaptain", Long.valueOf(10001), book));
    }
    
    @Test
    public void deleteBook() {
    	
    	Book book = new Book(10001, "bytecaptain", "Learn Java","This is a book");
    	
    	when(bookRepository.findById(Long.valueOf(10001))).thenReturn(Optional.of(book));
    	bookService.deleteBook("bytecaptain", Long.valueOf(10001));

		verify(bookRepository, times(1)).deleteById(Long.valueOf(10001));
    }
    
	@Test
    public void searchBook() {
		List<Book>books = Arrays.asList(
				new Book(10001, "bytecaptain", "Learn Java","This is a book"),
				new Book(10002, "bytecaptain", "Learn Java","This is another book"));
    	
    	when(bookRepository.findByAuthorAndTitle("bytecaptain", "Learn Java")).thenReturn(books);
		assertEquals(books, bookService.searchBookByAuthorAndTitle("bytecaptain", "Learn Java"));
    }
}
