package com.poscodx.mysite.exception;

@SuppressWarnings("serial")
public class UserRepositoryException extends RuntimeException {
	public UserRepositoryException() {
		super("UserRepositoryException Thrown");
	}
}
