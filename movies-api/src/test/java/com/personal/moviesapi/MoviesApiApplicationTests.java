package com.personal.moviesapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Times;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.personal.moviesapi.dto.MoviesDTO;
import com.personal.moviesapi.exceptionHandling.CustomMoviesBaseException;
import com.personal.moviesapi.exceptionHandling.MovieException.GeneralMovieDataException;
import com.personal.moviesapi.exceptionHandling.MovieException.NullMovieDataException;
import com.personal.moviesapi.exceptionHandling.MovieException.ResourceNotFoundException;
import com.personal.moviesapi.model.Movies;
import com.personal.moviesapi.repository.IMovieRepository;
import com.personal.moviesapi.service.MovieCatalogueService;

@RunWith(SpringRunner.class)
@SpringBootTest
class MoviesApiApplicationTests {

	@MockBean
	private IMovieRepository movieRepository;

	@Autowired
	private MovieCatalogueService catalogueService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Rule
	public MockitoRule rule = MockitoJUnit.rule();

	@Test
	public void testMovieSavedInDataBase() throws CustomMoviesBaseException {
		Movies mockMovies = new Movies(12, "BloodShot", 2020, 5);
		when(movieRepository.save(mockMovies)).thenReturn(new Movies(12, "BloodShot", 2020, 5));
		assertEquals(mockMovies.getMovieId(), movieRepository.save(mockMovies).getMovieId());
	}

	@Test
	public void test_getAllMoviesByRatings() throws CustomMoviesBaseException {
		int ratings = 4;
		when(movieRepository.findByRatingsGreaterThanEqual(ratings)).thenReturn(Optional.of(Stream
				.of(new Movies(1, "TERMINATOR", 1998, 4), new Movies(1, "XYZ", 2000, 5)).collect(Collectors.toList())));

		assertEquals(2, catalogueService.movieListByRatings(ratings).size());
	}

	@Test
	public void test_getAllMoviesByRatings_throwsException() throws CustomMoviesBaseException {
		int ratings = 4;
		Exception e = assertThrows(GeneralMovieDataException.class, () -> {
			catalogueService.movieListByRatings(ratings);
		});
		String expected_message = "Requested rating not found in Our Database.Please retry";
		String actual_message = e.getMessage();
		assertEquals(expected_message, actual_message);
	}

	@Test
	public void test_getAllMoviesByReleaseYear() throws CustomMoviesBaseException {
		int yearOfRelease = 2000;
		when(movieRepository.findByMovieReleaseYearGreaterThanEqual(yearOfRelease)).thenReturn(Optional.of(Stream
				.of(new Movies(1, "TERMINATOR", 2000, 4), new Movies(1, "XYZ", 2001, 5)).collect(Collectors.toList())));

		assertEquals(2, catalogueService.movieListByYearOfRelease(yearOfRelease).size());
	}

	@Test
	public void test_getAllMoviesByReleaseYear_throwsException() throws CustomMoviesBaseException {
		int yearOfRelease = 2000;
		Exception e = assertThrows(GeneralMovieDataException.class, () -> {
			catalogueService.movieListByYearOfRelease(yearOfRelease);
		});
		String expected_message = "Requested Year of release not found in Our Database.Please retry";
		String actual_message = e.getMessage();
		assertEquals(expected_message, actual_message);
	}

	@Test
	public void test_getAllMoviesByName() throws CustomMoviesBaseException {
		String name = "TERMINATOR";
		when(movieRepository.findByMovieName(name)).thenReturn(
				Optional.of(Stream.of(new Movies(1, "TERMINATOR", 2000, 4), new Movies(1, "TERMINATOR", 2001, 5))
						.collect(Collectors.toList())));

		assertEquals(2, catalogueService.movieListByName(name).size());
	}

	@Test
	public void test_getAllMoviesByName_throwsException() throws CustomMoviesBaseException {
		String name = "TERMINATOR";
		Exception e = assertThrows(GeneralMovieDataException.class, () -> {
			catalogueService.movieListByName(name);
		});
		String expected_message = "Requested Movie name not found in Our Database.Please retry";
		String actual_message = e.getMessage();
		assertEquals(expected_message, actual_message);
	}

	@Test
	public void test_update_In_Movie_Catalogue_Success() throws CustomMoviesBaseException {
		int id = 1;
		MoviesDTO dto = new MoviesDTO(1, "TERMINATOR", 2000, 4);

		when(movieRepository.findById(id)).thenReturn(Optional.of(new Movies(1, "TERMINATOR", 2000, 4)));
		assertEquals("TERMINATOR", catalogueService.update(id, dto).getMovieName());
	}

	@Test
	public void test_update_In_Movie_Catalogue_throwsNullException() throws CustomMoviesBaseException {
		int id = 1;
		MoviesDTO dto = null;
		Exception e = assertThrows(NullMovieDataException.class, () -> {
			catalogueService.update(id, dto);
		});

		String expected_message = "Movie request form for data insertion is null";
		String actual_message = e.getMessage();
		assertEquals(expected_message, actual_message);
	}

	@Test
	public void test_update_In_Movie_Catalogue_throwsResourceException() throws CustomMoviesBaseException {
		int id = 1;
		MoviesDTO dto = new MoviesDTO(2, "TERMINATOR", 2000, 4);
		Exception e = assertThrows(ResourceNotFoundException.class, () -> {
			catalogueService.update(id, dto);
		});

		String expected_message = "Requested Id not found in Our Database.Please retry";
		String actual_message = e.getMessage();
		assertEquals(expected_message, actual_message);
	}

	@Test
	public void test_delete_movieInCatalogue() throws CustomMoviesBaseException {
		Movies mockMovieItem = new Movies(2, "TERMINATOR", 2000, 4);
		catalogueService.delete(2);
		verify(movieRepository, times(1)).deleteById(mockMovieItem.getMovieId());
		;
	}

	@Test
	public void test_delete_movieInCatalogue_recordNotPresent() throws CustomMoviesBaseException {
		int id = 0;
		Exception e = assertThrows(NullMovieDataException.class, () -> {
			catalogueService.delete(id);
		});
		String expected_message = " Id for Deletion is zero.Please provide a valid Id";
		String actual_message = e.getMessage();
		assertEquals(expected_message, actual_message);
	}
	

}
