package com.personal.moviesapi.dto;

import lombok.Data;

@Data
public class MoviesDTO {

	private int movieId;

	

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public int getMovieReleaseYear() {
		return movieReleaseYear;
	}

	public MoviesDTO() {
		
	}
	public MoviesDTO(int movieId, String movieName, int movieReleaseYear, int ratings) {
		super();
		this.movieId = movieId;
		this.movieName = movieName;
		this.movieReleaseYear = movieReleaseYear;
		this.ratings = ratings;
	}

	public void setMovieReleaseYear(int movieReleaseYear) {
		this.movieReleaseYear = movieReleaseYear;
	}

	public int getRatings() {
		return ratings;
	}

	public void setRatings(int ratings) {
		this.ratings = ratings;
	}

	private String movieName;

	private int movieReleaseYear;

	private int ratings;

}
