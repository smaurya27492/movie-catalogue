package com.personal.moviesapi.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.personal.moviesapi.exceptionHandling.MovieException.GeneralMovieDataException;
import com.personal.moviesapi.exceptionHandling.MovieException.IllegalMovieInputFormatException;
import com.personal.moviesapi.exceptionHandling.MovieException.NullMovieDataException;
import com.personal.moviesapi.exceptionHandling.MovieException.ResourceNotFoundException;

@RestControllerAdvice
public class MovieCatalogueExceptionHandler {

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<ErrorItem> handle(ResourceNotFoundException e) {
		ErrorItem error = new ErrorItem();
		error.setMessage(e.getMessage());
		error.setStatusCode(e.getStatusCode());

		return new ResponseEntity<ErrorItem>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = GeneralMovieDataException.class)
	public ResponseEntity<ErrorItem> handle(GeneralMovieDataException e) {
		ErrorItem error = new ErrorItem();
		error.setMessage(e.getMessage());
		error.setStatusCode(e.getStatusCode());

		return new ResponseEntity<ErrorItem>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = IllegalMovieInputFormatException.class)
	public ResponseEntity<ErrorItem> handle(IllegalMovieInputFormatException e) {
		ErrorItem error = new ErrorItem();
		error.setMessage(e.getMessage());
		error.setStatusCode(e.getStatusCode());

		return new ResponseEntity<ErrorItem>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = NullMovieDataException.class)
	public ResponseEntity<ErrorItem> handle(NullMovieDataException e) {
		ErrorItem error = new ErrorItem();
		error.setMessage(e.getMessage());
		error.setStatusCode(e.getStatusCode());

		return new ResponseEntity<ErrorItem>(error, HttpStatus.BAD_REQUEST);
	}
}
