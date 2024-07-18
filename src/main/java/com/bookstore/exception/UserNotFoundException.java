package com.bookstore.exception;

public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 179856888745840942L;
	
	public UserNotFoundException(Long id) {
		super("User id not found : " + id);
	}
	public UserNotFoundException(String message,Long id) {
		super("User id not found : " + id + "TODO" + message);
	}
}
