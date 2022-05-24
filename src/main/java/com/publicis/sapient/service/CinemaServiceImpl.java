package com.publicis.sapient.service;

import com.publicis.sapient.entity.Cinema;
import com.publicis.sapient.entity.City;
import com.publicis.sapient.exception.ApiException;
import com.publicis.sapient.repository.CinemaRepository;
import com.publicis.sapient.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaServiceImpl implements CinemaService {

	@Autowired
	private CinemaRepository cinemaRepository;

	@Autowired
	private CityRepository cityRepository;

	public CinemaServiceImpl(CinemaRepository cinemaRepository) {
		this.cinemaRepository = cinemaRepository;
	}

	@Override
	public Cinema addCinema(Cinema cinema,Integer cityId) throws ApiException {
		City city = cityRepository.findById(cityId).get();
		cinema.setCity(city);
		cinemaRepository.saveAndFlush(cinema);
		return cinema;
	}

	@Override
	public List<Cinema> viewCinemaList() {
		return cinemaRepository.findAll();
	}

	@Override
	public Cinema removeCinema(int cinemaId) {
		Cinema c = cinemaRepository.findById(cinemaId).get();
		cinemaRepository.delete(c);
		return c;
	}

	@Override
	public Cinema updateCinema(Cinema cinema) throws ApiException {

		Cinema cu = cinemaRepository.findById(cinema.getCinemaId()).get();
		if(cu.getCinemaId() == 0) {
			throw new ApiException(HttpStatus.NOT_FOUND,"Cinema not available");
		}
		cinemaRepository.saveAndFlush(cinema);
		return cinemaRepository.findById(cinema.getCinemaId()).get();
	}

	@Override
	public Cinema viewCinema(int cinemaId) {
		return cinemaRepository.findById(cinemaId).get();
	}

}
