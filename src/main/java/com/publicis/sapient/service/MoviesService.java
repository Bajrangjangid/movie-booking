package com.publicis.sapient.service;

import com.publicis.sapient.entity.Movie;
import com.publicis.sapient.exception.ApiException;

import java.time.LocalDate;
import java.util.List;

public interface MoviesService {

	Movie addMovie(Movie movie) throws ApiException;

	Movie removeMovie(int movieId) throws ApiException;

	Movie updateMovie(Movie movie) throws ApiException;

	Movie addMovieToShow(Movie movie, Integer showId) throws ApiException;

	Movie viewMovie(int movieId) throws ApiException;

	List<Movie> viewMovieList() throws ApiException;

}
