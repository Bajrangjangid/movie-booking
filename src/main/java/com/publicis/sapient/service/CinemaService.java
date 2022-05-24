package com.publicis.sapient.service;

import com.publicis.sapient.entity.Cinema;
import com.publicis.sapient.exception.ApiException;

import java.util.List;

public interface CinemaService {

	Cinema addCinema(Cinema cinema,Integer cityId) throws ApiException;

	Cinema removeCinema(int cinemaId) throws ApiException;

	Cinema updateCinema(Cinema cinema) throws ApiException;

	Cinema viewCinema(int cinemaId) throws ApiException;

	List<Cinema> viewCinemaList() throws ApiException;
}
