package com.webtech.blogpost.blogPost.exception;

public class BlogPostException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BlogPostException(String exMessage, Exception exception) {
		super(exMessage, exception);
	}

	public BlogPostException(String exMessage) {
		super(exMessage);
	}
}