package com.personal.moviesapi.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Movie_Catalogue")
@EqualsAndHashCode(of="movieId")
public class Movies {

	@Id
	@GeneratedValue
	private int movieId;

	

	public Movies(int movieId, String movieName, int movieReleaseYear, int ratings) {
		super();
		this.movieId = movieId;
		this.movieName = movieName;
		this.movieReleaseYear = movieReleaseYear;
		this.ratings = ratings;
	}

	public Movies() {
		// TODO Auto-generated constructor stub
	}

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

	public void setMovieReleaseYear(int movieReleaseYear) {
		this.movieReleaseYear = movieReleaseYear;
	}

	public int getRatings() {
		return ratings;
	}

	public void setRatings(int ratings) {
		this.ratings = ratings;
	}

	@Basic(optional = false)
	private String movieName;
	private int movieReleaseYear;
	private int ratings;

}
