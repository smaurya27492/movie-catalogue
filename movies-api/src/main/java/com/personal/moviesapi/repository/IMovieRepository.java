package com.personal.moviesapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.personal.moviesapi.model.Movies;

@Repository
public interface IMovieRepository extends CrudRepository<Movies, Integer> {

	Optional<Movies> findById(int id);

	Optional<List<Movies>> findByRatings(int ratings);

	Optional<List<Movies>> findByRatingsGreaterThanEqual(int rating);

	Optional<List<Movies>> findByMovieReleaseYearGreaterThanEqual(int yearOfRelease);

	Optional<List<Movies>> findByMovieName(String movieName);

}
