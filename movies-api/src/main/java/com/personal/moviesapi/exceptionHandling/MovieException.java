package com.personal.moviesapi.exceptionHandling;

@SuppressWarnings("serial")
public class MovieException {

	// When the queried resource is not available throw this exception
	public static class ResourceNotFoundException extends CustomMoviesBaseException {

		public ResourceNotFoundException(String message,int status) {
			super(message,status);
		}
	}

	// When the input provided to APIs are not in a proper format //
	public static class IllegalMovieInputFormatException extends CustomMoviesBaseException {

		public IllegalMovieInputFormatException(String message,int status) {
			super(message,status);
		}
	}

	// when the expected input from the API is empty //
	public static class NullMovieDataException extends CustomMoviesBaseException {

		public NullMovieDataException(String message,int status) {
			super(message,status);
		}
	}

	// For throwing all general Exception //
	public static class GeneralMovieDataException extends CustomMoviesBaseException {

		public GeneralMovieDataException(String message,int status) {
			super(message,status);
		}
	}

}
