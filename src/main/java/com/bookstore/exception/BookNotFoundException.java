package com.bookstore.exception;

public class BookNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 179856888745840942L;
	
	public BookNotFoundException(Long id) {
		super("Book id not found : " + id);
	}
	public BookNotFoundException(String message,Long id) {
		super("Book id not found : " + id + "TODO" + message);
	}
}
