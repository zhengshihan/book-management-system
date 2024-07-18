package com.bookstore.exception;

public class GroupNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 179856888745840942L;
	
	public GroupNotFoundException(Long id) {
		super("Group id not found : " + id);
	}
	public GroupNotFoundException(String message,Long id) {
		super("Group id not found : " + id + "TODO" + message);
	}
}
