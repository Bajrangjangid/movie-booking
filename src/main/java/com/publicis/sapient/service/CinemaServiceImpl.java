package com.publicis.sapient.service;

import com.publicis.sapient.entity.Cinema;
import com.publicis.sapient.exception.ApiException;
import com.publicis.sapient.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaServiceImpl implements CinemaService {

	@Autowired
	private CinemaRepository cinemaRepository;

	public CinemaServiceImpl(CinemaRepository cinemaRepository) {
		this.cinemaRepository = cinemaRepository;
	}

	@Override
	public Cinema addCinema(Cinema cinema) throws ApiException {
		cinemaRepository.saveAndFlush(cinema);
		return cinema;
	}

	@Override
	public List<Cinema> viewCinemaList() {
		return cinemaRepository.findAll();
	}

	@Override
	public Cinema removeCinema(int cinemaId) {
		Cinema c = cinemaRepository.getOne(cinemaId);
		cinemaRepository.delete(c);
		return c;
	}

	@Override
	public Cinema updateCinema(Cinema cinema) throws ApiException {

		Cinema cu = cinemaRepository.getOne(cinema.getCinemaId());
		if(cu.getCinemaId() == 0) {
			throw new ApiException(HttpStatus.NOT_FOUND,"Cinema not available");
		}
		cinemaRepository.saveAndFlush(cinema);
		return cinemaRepository.getOne(cinema.getCinemaId());
	}

	@Override
	public Cinema viewCinema(int cinemaId) {
		return cinemaRepository.findById(cinemaId).get();
	}

}
