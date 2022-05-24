package com.publicis.sapient.service;

import com.publicis.sapient.entity.Movie;
import com.publicis.sapient.entity.Show;
import com.publicis.sapient.exception.ApiException;
import com.publicis.sapient.repository.MoviesRepository;
import com.publicis.sapient.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoviesServiceImpl implements MoviesService {

	@Autowired
	private MoviesRepository moviesRepository;

	@Autowired
	ShowRepository showrepository;

	@Override
	public Movie addMovie(Movie movie) throws ApiException {
		moviesRepository.saveAndFlush(movie);
		return movie;
	}

	@Override
	public Movie removeMovie(int movieId) {
		Movie m = moviesRepository.findById(movieId).get();
		List<Show> showList = showrepository.findAll();
		for (Show show : showList) {
			if (show.getMovie()!=null && show.getMovie().getMovieId() == movieId) {
				show.setMovie(null);
			}
		}

		moviesRepository.delete(m);
		return m;
	}

	@Override
	public Movie updateMovie(Movie movie) {
		moviesRepository.saveAndFlush(movie);
		return moviesRepository.findById(movie.getMovieId()).get();
	}

	@Override
	public Movie addMovieToShow(Movie movie,Integer showId) {
		moviesRepository.saveAndFlush(movie);
		return moviesRepository.findById(movie.getMovieId()).get();
	}

	@Override
	public Movie viewMovie(int movieId) {
		return moviesRepository.findById(movieId).get();
	}

	@Override
	public List<Movie> viewMovieList() throws ApiException {
		List<Movie> ml = moviesRepository.findAll();
		return ml;
	}

}
