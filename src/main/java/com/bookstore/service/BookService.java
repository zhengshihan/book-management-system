package com.bookstore.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bookstore.model.Book;

public interface BookService {
	List<Book> getAllBooksByAuthor(String author);
//	List<Book> getAllBooks();
	Book getBook(String author, long id);
	
	Page<Book> getAllBooks(Pageable pageable);
	void deleteBook(String author, long id);
	Book updateBook(String author, long id, Book book);
	Book createBook(String author, Book book);
	List<Book>searchBookByAuthorAndTitle(String author, String title);
	List<Book> getBooksByAuthorAfterYear(String author, int year);
}
