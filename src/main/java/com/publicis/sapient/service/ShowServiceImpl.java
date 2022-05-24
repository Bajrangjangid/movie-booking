package com.publicis.sapient.service;

import com.publicis.sapient.domain.ShowStatus;
import com.publicis.sapient.entity.CinemaHall;
import com.publicis.sapient.entity.Movie;
import com.publicis.sapient.entity.Show;
import com.publicis.sapient.repository.CinemaHallRepository;
import com.publicis.sapient.repository.MoviesRepository;
import com.publicis.sapient.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowServiceImpl implements ShowService {
	@Autowired
	private ShowRepository showRepository;

	@Autowired
	private MoviesRepository moviesRepository;

	@Autowired
	private CinemaHallRepository cinemaHallRepository;

	@Override
	public Show addShow(Show show, Integer cinemaHallId, Integer movieId) {

		if (cinemaHallId != null) {
			CinemaHall cinemaHall = cinemaHallRepository.getOne(cinemaHallId);
			show.setCinemaHall(cinemaHall);
		}
		if (movieId != null) {
			Movie movie = moviesRepository.getOne(movieId);
			show.setMovie(movie);
		}
		showRepository.saveAndFlush(show);
		return show;
	}

	@Override
	public Show updateShow(Show show, Integer cinemaHallId, Integer movieId) {

		if (cinemaHallId != null) {
			CinemaHall cinemaHall = cinemaHallRepository.getOne(cinemaHallId);
			show.setCinemaHall(cinemaHall);
		}
		if (movieId != null) {
			Movie movie = moviesRepository.getOne(movieId);
			show.setMovie(movie);
		}
		showRepository.saveAndFlush(show);
		return show;
	}

	@Override
	public Show removeShow(int showId) {
		Show s = showRepository.findById(showId).get();
		showRepository.delete(s);
		return s;
	}

	@Override
	public Show viewShow(int showId) {
		return showRepository.findById(showId).get();
	}

	@Override
	public List<Show> viewAllShows() {
		List<Show> shows = showRepository.findAll();
		shows.forEach(show -> show.setShowStatus(this.getShowStatus(show)));
		return shows;
	}

	@Override
	public List<Show> viewShowList(int cinemaHallId) {
		List<Show> shList = showRepository.getAllByCinemaHallId(cinemaHallId);
		return shList;
	}

	@Override
	public List<Show> viewShowListByDate(LocalDate date) {
		List<Show> showList = showRepository.findAll();
		List<Show> shList = showList.stream()
				.filter(show -> (show.getShowDate() != null && show.getShowDate().isEqual(date)))
				.collect(Collectors.toList());
		return shList;
	}

	private ShowStatus getShowStatus(Show show) {
		LocalTime currentTime = LocalTime.now();
		LocalTime startTime = show.getShowStartTime();
		LocalTime endTime = show.getShowEndTime();
		LocalDate currentDate = LocalDate.now();
		ShowStatus showStatus = ShowStatus.EXPIRED;
		if (!show.getShowDate().isBefore(currentDate) && !currentDate.isAfter(show.getShowDate())) {
			if (startTime.isAfter(currentTime) && endTime.isBefore(currentTime)) {
				showStatus = ShowStatus.RUNNING;
			} else if (currentTime.isAfter(endTime)) {
				showStatus = ShowStatus.UPCOMING;
			}
		}
		return showStatus;
	}
}
