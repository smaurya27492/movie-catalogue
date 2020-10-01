package com.personal.moviesapi.exceptionHandling;

/**
 * 
 * @author smaurya
 * @implNote Custom abstract class so that all exception classes are bound to
 *           this base exception class
 *
 */
@SuppressWarnings("serial")
public abstract class CustomMoviesBaseException extends Exception {

	private String message;
	private int statusCode;

	public CustomMoviesBaseException(String message, int statusCode) {
		this.message = message;
		this.statusCode = statusCode;
	}

	// message to be retrieved using this accessor method
	public String getMessage() {
		return message;
	}

	public int getStatusCode() {
		return statusCode;
	}

}
