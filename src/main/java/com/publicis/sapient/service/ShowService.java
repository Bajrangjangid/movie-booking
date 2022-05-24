package com.publicis.sapient.service;

import com.publicis.sapient.entity.Show;
import com.publicis.sapient.exception.ApiException;

import java.time.LocalDate;
import java.util.List;

public interface ShowService {

	Show addShow(Show show, Integer cinemaHallId, Integer movieId) throws ApiException;

	Show updateShow(Show show, Integer cinemaHallId, Integer movieId) throws ApiException;

	Show removeShow(int showId) throws ApiException;

	Show viewShow(int showId) throws ApiException;

	List<Show> viewAllShows() throws ApiException;

	List<Show> viewShowList(int cinemaHallId) throws ApiException;

	List<Show> viewShowListByDate(LocalDate date) throws ApiException;

}
