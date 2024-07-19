package com.bookstore.service;

import java.util.List;

import com.bookstore.model.Book;

public interface BookService {
	List<Book> getAllBooks(String author);
	Book getBook(String author, long id);
	void deleteBook(String author, long id);
	Book updateBook(String author, long id, Book book);
	Book createBook(String author, Book book);
	List<Book>searchBookByAuthorAndTitle(String author, String title);
}
