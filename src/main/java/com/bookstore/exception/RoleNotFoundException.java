package com.bookstore.exception;

public class RoleNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 179856888745840942L;
	
	public RoleNotFoundException(Long id) {
		super("User id not found : " + id);
	}
	public RoleNotFoundException(String message,Long id) {
		super("User id not found : " + id + "TODO" + message);
	}
}
