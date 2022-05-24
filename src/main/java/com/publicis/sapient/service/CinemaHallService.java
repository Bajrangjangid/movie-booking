package com.publicis.sapient.service;

import com.publicis.sapient.entity.CinemaHall;
import com.publicis.sapient.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CinemaHallService {

	CinemaHall addCinemaHall(CinemaHall cinemaHall,Integer cinemaId) throws ApiException;

	CinemaHall removeCinemaHall(int cinemaHallId) throws ApiException;

	CinemaHall updateCinemaHall(CinemaHall cinemaHall,Integer cinemaId) throws ApiException;

	CinemaHall viewCinemaHall(int cinemaHallId) throws ApiException;

	List<CinemaHall> viewCinemaHallList() throws ApiException;
}