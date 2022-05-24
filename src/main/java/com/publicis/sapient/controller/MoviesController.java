package com.publicis.sapient.controller;

import com.publicis.sapient.constant.Constants;
import com.publicis.sapient.entity.Movie;
import com.publicis.sapient.exception.ApiException;
import com.publicis.sapient.service.MoviesService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MoviesController {

	Logger logger = LoggerFactory.getLogger(MoviesController.class);

	@Autowired
	private MoviesService moviesService;

	@PostMapping("/movie")
	public ResponseEntity<Movie> addMovie(@RequestBody Movie movie)
			throws ApiException {
		try {
			movie = moviesService.addMovie(movie);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(movie, HttpStatus.CREATED);
	}


	@PutMapping("/movie/{movieId}")
	public ResponseEntity<Movie> updateMovie(@PathVariable int movieId,@RequestBody Movie movie)
			throws ApiException {
		try {
			if (movie == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			movie.setMovieId(movieId);
			movie = moviesService.updateMovie(movie);

		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(movie, HttpStatus.ACCEPTED);
	}

	@PutMapping("/movie/show")
	public ResponseEntity<Movie> addToShow(@RequestBody Movie movie,@RequestParam(required = false) Integer showId)
			throws ApiException {
		try {
			if (movie == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			movie = moviesService.addMovieToShow(movie,showId);

		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(movie, HttpStatus.ACCEPTED);
	}


	@GetMapping("/movies")
	public ResponseEntity<List<Movie>> viewMovieList() throws ApiException {
		List<Movie> movies = null;
		try {
			movies = moviesService.viewMovieList();
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return ResponseEntity.ok(movies);
	}

	@GetMapping("/movie/{movieId}")
	public ResponseEntity<Movie> viewMovie(@PathVariable int movieId) throws ApiException {
		Movie movie = null;
		try {
			movie = moviesService.viewMovie(movieId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(movie, HttpStatus.OK);
	}

	@DeleteMapping("/movie/{movieId}")
	public ResponseEntity<Movie> removeMovie(@PathVariable int movieId)
			throws ApiException {
		Movie movie = null;
		try {
			movie = moviesService.viewMovie(movieId);
			if (movie == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			moviesService.removeMovie(movieId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(movie, HttpStatus.OK);
	}
}
