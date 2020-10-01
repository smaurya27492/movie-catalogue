package com.personal.moviesapi.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import com.personal.moviesapi.dto.MoviesDTO;
import com.personal.moviesapi.exceptionHandling.MovieException;
import com.personal.moviesapi.exceptionHandling.MovieException.GeneralMovieDataException;
import com.personal.moviesapi.exceptionHandling.MovieException.IllegalMovieInputFormatException;
import com.personal.moviesapi.exceptionHandling.MovieException.NullMovieDataException;
import com.personal.moviesapi.exceptionHandling.MovieException.ResourceNotFoundException;
import com.personal.moviesapi.model.Movies;
import com.personal.moviesapi.repository.IMovieRepository;

@Service
@Transactional(readOnly = true)
public class MovieCatalogueService {

	@Autowired
	private IMovieRepository movieRepository;

	/**
	 * Method to create a new movie in movie DB
	 * 
	 * @param movieDto
	 * @return
	 * @throws NullMovieDataException
	 * @throws IllegalMovieInputFormatException
	 */
	@Transactional
	public MoviesDTO create(MoviesDTO movieDto) throws NullMovieDataException, IllegalMovieInputFormatException {
		if (Objects.isNull(movieDto)) {
			throw new NullMovieDataException("Movie request form for data insertion is null", 400);
		}

		if (!(movieDto.getMovieName() instanceof String)) {
			throw new MovieException.IllegalMovieInputFormatException("Input format is not correct please check", 404);

		}
		Movies movies = new Movies();
		try {
			movies.setMovieId(movieDto.getMovieId());
			movies.setMovieName(movieDto.getMovieName());
			movies.setMovieReleaseYear(movieDto.getMovieReleaseYear());
			movies.setRatings(movieDto.getRatings());
			System.out.println("Movie Bean :" + movies.getMovieId() + movies.getMovieName());
		} catch (Exception e) {
			System.out.println("Exception in converting values");
		}
		Movies movies2 = movieRepository.save(movies);
		System.out.println("Getting from DB :" + movies2);
		MoviesDTO moviesDTO2 = new MoviesDTO();
		moviesDTO2.setMovieId(movies2.getMovieId());
		moviesDTO2.setMovieName(movies2.getMovieName());
		moviesDTO2.setMovieReleaseYear(movies2.getMovieReleaseYear());
		moviesDTO2.setRatings(movies2.getRatings());

		return moviesDTO2;
	}

	/**
	 * Method to update an existing movie of
	 * 
	 * @param id
	 * @param dto
	 * @return
	 * @throws ResourceNotFoundException
	 * @throws NullMovieDataException
	 * @throws IllegalMovieInputFormatException
	 */
	@Transactional
	public MoviesDTO update(int id, MoviesDTO dto)
			throws ResourceNotFoundException, NullMovieDataException, IllegalMovieInputFormatException {
		if (Objects.isNull(dto)) {
			throw new NullMovieDataException("Movie request form for data insertion is null", 400);
		}
//		if(!(dto.getMovieId() instanceof String)||!(dto.getMovieName() instanceof String)) {
//			throw new MovieException.IllegalMovieInputFormatException("Input format is not correct please check", 404);
//			
//		}

		Movies movies = movieRepository.findById(id).orElseThrow(() -> new MovieException.ResourceNotFoundException(
				"Requested Id not found in Our Database.Please retry", 404));
		movies.setMovieName(dto.getMovieName());
		movies.setMovieReleaseYear(dto.getMovieReleaseYear());
		movies.setRatings(dto.getRatings());
		movieRepository.save(movies);
		MoviesDTO moviesDTO2 = new MoviesDTO();
		moviesDTO2.setMovieId(movies.getMovieId());
		moviesDTO2.setMovieName(movies.getMovieName());
		moviesDTO2.setMovieReleaseYear(movies.getMovieReleaseYear());
		moviesDTO2.setRatings(movies.getRatings());
		return (moviesDTO2);
	}

	/**
	 * Method to delete any movie from the Database
	 * 
	 * @param id
	 * @throws NullMovieDataException
	 * @throws GeneralMovieDataException
	 */
	@Transactional
	public void delete(int id) throws NullMovieDataException, GeneralMovieDataException {
		if (id == 0) {
			throw new NullMovieDataException(" Id for Deletion is zero.Please provide a valid Id", 400);
		}
		movieRepository.deleteById(id);
	}

	public List<MoviesDTO> movieListByRatings(int rating) throws GeneralMovieDataException {

		List<MoviesDTO> movieListByratings = movieRepository.findByRatingsGreaterThanEqual(rating)
				.orElseThrow(() -> new MovieException.GeneralMovieDataException(
						"Requested rating not found in Our Database.Please retry", 400))

				.stream().map(movies -> {
					MoviesDTO dto = new MoviesDTO();

					dto.setMovieId(movies.getMovieId());
					dto.setMovieName(movies.getMovieName());
					dto.setMovieReleaseYear(movies.getMovieReleaseYear());
					dto.setRatings(movies.getRatings());

					return dto;
				}).collect(Collectors.toList());
		return movieListByratings;
	}

	public List<MoviesDTO> movieListByYearOfRelease(int yearOfRelease) throws GeneralMovieDataException {

		List<MoviesDTO> movieListByYearOfRelease = movieRepository.findByMovieReleaseYearGreaterThanEqual(yearOfRelease)
				.orElseThrow(() -> new MovieException.GeneralMovieDataException(
						"Requested Year of release not found in Our Database.Please retry", 400))
				.stream().map(movies -> {
					MoviesDTO dto = new MoviesDTO();

					dto.setMovieId(movies.getMovieId());

					dto.setMovieName(movies.getMovieName());
					dto.setMovieReleaseYear(movies.getMovieReleaseYear());
					dto.setRatings(movies.getRatings());

					return dto;
				}).collect(Collectors.toList());

		return movieListByYearOfRelease;
	}

	public List<MoviesDTO> movieListByName(String movieName) throws GeneralMovieDataException {

		List<MoviesDTO> movieListByName = movieRepository.findByMovieName(movieName)
				.orElseThrow(() -> new MovieException.GeneralMovieDataException(
						"Requested Movie name not found in Our Database.Please retry", 400))

				.stream().map(movies -> {
					MoviesDTO dto = new MoviesDTO();

					dto.setMovieId(movies.getMovieId());
					dto.setMovieName(movies.getMovieName());
					dto.setMovieReleaseYear(movies.getMovieReleaseYear());
					dto.setRatings(movies.getRatings());

					return dto;
				}).collect(Collectors.toList());
		return movieListByName;
	}
}
