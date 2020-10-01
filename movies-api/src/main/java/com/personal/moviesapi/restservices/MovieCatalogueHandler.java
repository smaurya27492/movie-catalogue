package com.personal.moviesapi.restservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.moviesapi.dto.MoviesDTO;
import com.personal.moviesapi.exceptionHandling.CustomMoviesBaseException;
import com.personal.moviesapi.responsemodel.APIResponse;
import com.personal.moviesapi.service.MovieCatalogueService;

import lombok.NonNull;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "*")
@SuppressWarnings({ "rawtypes" })
public class MovieCatalogueHandler {

	@Autowired
	private MovieCatalogueService movieCatalogueService;

	@PostMapping("/create")
	public ResponseEntity<APIResponse> createMoviesInCatalogue(@Validated @RequestBody MoviesDTO movieForm) {
		APIResponse genericResponse = new APIResponse();
		try {
			MoviesDTO movieDTO = movieCatalogueService.create(movieForm);
			genericResponse.setResponse(movieDTO);
			return new ResponseEntity<APIResponse>(genericResponse, HttpStatus.OK);

		} catch (CustomMoviesBaseException e) {
			genericResponse.setError(e.getMessage());
			return new ResponseEntity<APIResponse>(genericResponse, HttpStatus.BAD_REQUEST);

		}

	}

	@PutMapping("/update/{movieId}")
	public ResponseEntity<APIResponse> updateMoviesInCatalogue(@PathVariable @NonNull int movieId,
			@RequestBody @NonNull MoviesDTO movieDTO) {

		APIResponse genericResponse = new APIResponse();
		try {
			MoviesDTO moviesDto = movieCatalogueService.update(movieId, movieDTO);
			genericResponse.setResponse(moviesDto);
			return new ResponseEntity<APIResponse>(genericResponse, HttpStatus.OK);

		} catch (CustomMoviesBaseException e) {
			genericResponse.setError(e.getMessage());
			return new ResponseEntity<APIResponse>(genericResponse, HttpStatus.BAD_REQUEST);

		}

	}

	@GetMapping("/fetch/movieByRatings/{rating}")
	@Cacheable(cacheNames="movieCache", condition="#id > 1")
	public ResponseEntity<APIResponse> getMoviesByRatings(@PathVariable @NonNull int rating) {

		APIResponse genericResponse = new APIResponse();

		try {
			List<MoviesDTO> movieListByRating = movieCatalogueService.movieListByRatings(rating);
			genericResponse.setResponse(movieListByRating);
			return new ResponseEntity<APIResponse>(genericResponse, HttpStatus.OK);

		} catch (CustomMoviesBaseException e) {
			genericResponse.setError(e.getMessage());
			return new ResponseEntity<APIResponse>(genericResponse, HttpStatus.BAD_REQUEST);

		}

	}

	@GetMapping("/fetch/movieByRelease/{yearOfRelease}")
	public ResponseEntity<APIResponse> getMoviesByYear(@PathVariable @NonNull int yearOfRelease) {

		APIResponse genericResponse = new APIResponse();

		try {
			List<MoviesDTO> movieListByYearOfRelease = movieCatalogueService.movieListByYearOfRelease(yearOfRelease);
			genericResponse.setResponse(movieListByYearOfRelease);
			return new ResponseEntity<APIResponse>(genericResponse, HttpStatus.OK);

		} catch (CustomMoviesBaseException e) {
			genericResponse.setError(e.getMessage());
			return new ResponseEntity<APIResponse>(genericResponse, HttpStatus.BAD_REQUEST);

		}

	}

	@GetMapping("/fetch/movieByName/{name}")
	public ResponseEntity<APIResponse> getMoviesByName(@PathVariable @NonNull String name) {

		APIResponse genericResponse = new APIResponse();

		try {
			List<MoviesDTO> movieListByName = movieCatalogueService.movieListByName(name);
			genericResponse.setResponse(movieListByName);
			return new ResponseEntity<APIResponse>(genericResponse, HttpStatus.OK);

		} catch (CustomMoviesBaseException e) {
			genericResponse.setError(e.getMessage());
			return new ResponseEntity<APIResponse>(genericResponse, HttpStatus.BAD_REQUEST);

		}

	}

	@GetMapping("/delete/{id}")
	public ResponseEntity deleteMovieById(@PathVariable @NonNull int id) throws Exception {
		APIResponse genericResponse = new APIResponse();

		try {
			movieCatalogueService.delete(id);
			genericResponse.setResponse("Resource Deleted");
			return new ResponseEntity<APIResponse>(genericResponse, HttpStatus.OK);

		} catch (Exception e) {
			genericResponse.setError("Error in deleting the resource");
			return new ResponseEntity<APIResponse>(genericResponse, HttpStatus.BAD_REQUEST);

		}

	}
}
