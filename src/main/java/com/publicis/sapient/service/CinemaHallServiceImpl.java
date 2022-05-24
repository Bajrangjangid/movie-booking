package com.publicis.sapient.service;

import com.publicis.sapient.entity.Cinema;
import com.publicis.sapient.entity.CinemaHall;
import com.publicis.sapient.exception.ApiException;
import com.publicis.sapient.repository.CinemaHallRepository;
import com.publicis.sapient.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaHallServiceImpl implements CinemaHallService {

	@Autowired
	private CinemaHallRepository cinemaHallRepository;

	@Autowired
	private CinemaRepository cinemaRepository;

	public CinemaHallServiceImpl(CinemaHallRepository cinemaHallRepository) {
		this.cinemaHallRepository = cinemaHallRepository;
	}

	@Override
	public CinemaHall addCinemaHall(CinemaHall cinemaHall,Integer cinemaId) throws ApiException {
		Cinema cinema = cinemaRepository.findById(cinemaId).get();
		cinemaHall.setCinema(cinema);

		cinemaHallRepository.saveAndFlush(cinemaHall);
		return cinemaHall;
	}

	@Override
	public List<CinemaHall> viewCinemaHallList() {
		return cinemaHallRepository.findAll();
	}

	@Override
	public CinemaHall removeCinemaHall(int cinemaHallId) {
		CinemaHall c = cinemaHallRepository.getOne(cinemaHallId);
		cinemaHallRepository.delete(c);
		return c;
	}

	@Override
	public CinemaHall updateCinemaHall(CinemaHall cinemaHall,Integer cinemaId) throws ApiException {

		Cinema cinema = cinemaRepository.findById(cinemaId).get();
		cinemaHall.setCinema(cinema);

		CinemaHall cu = cinemaHallRepository.getOne(cinemaHall.getCinemaHallId());
		if(cu.getCinemaHallId() == 0) {
			throw new ApiException(HttpStatus.NOT_FOUND,"Cinema seat not available");
		}
		cinemaHallRepository.saveAndFlush(cinemaHall);
		return cinemaHallRepository.getOne(cinemaHall.getCinemaHallId());
	}

	@Override
	public CinemaHall viewCinemaHall(int cinemaHallId) {
		return cinemaHallRepository.findById(cinemaHallId).get();
	}

}
