package com.publicis.sapient.service;

import com.publicis.sapient.entity.CinemaHall;
import com.publicis.sapient.entity.CinemaSeat;
import com.publicis.sapient.exception.ApiException;
import com.publicis.sapient.repository.CinemaHallRepository;
import com.publicis.sapient.repository.CinemaSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaSeatServiceImpl implements CinemaSeatService {

	@Autowired
	private CinemaSeatRepository cinemaSeatRepository;

	@Autowired
	private CinemaHallRepository cinemaHallRepository;

	public CinemaSeatServiceImpl(CinemaSeatRepository cinemaSeatRepository) {
		this.cinemaSeatRepository = cinemaSeatRepository;
	}

	@Override
	public List<CinemaSeat> addCinemaSeat(List<CinemaSeat> cinemaSeats,Integer cinemaHallId) throws ApiException {
		CinemaHall cinemaHall = cinemaHallRepository.findById(cinemaHallId).get();
		cinemaSeats.forEach(cinemaSeat -> cinemaSeat.setCinemaHall(cinemaHall));

		cinemaSeatRepository.saveAll(cinemaSeats);
		return cinemaSeats;
	}

	@Override
	public List<CinemaSeat> viewCinemaSeatList() {
		return cinemaSeatRepository.findAll();
	}

	@Override
	public CinemaSeat removeCinemaSeat(int cinemaSeatId) {
		CinemaSeat c = cinemaSeatRepository.findById(cinemaSeatId).get();
		cinemaSeatRepository.delete(c);
		return c;
	}

	@Override
	public CinemaSeat updateCinemaSeat(CinemaSeat cinemaSeat,Integer cinemaHallId) throws ApiException {

		CinemaHall cinemaHall = cinemaHallRepository.findById(cinemaHallId).get();
		cinemaSeat.setCinemaHall(cinemaHall);

		CinemaSeat cu = cinemaSeatRepository.findById(cinemaSeat.getCinemaSeatId()).get();
		if(cu.getCinemaSeatId() == 0) {
			throw new ApiException(HttpStatus.NOT_FOUND,"Cinema seat not available");
		}
		cinemaSeatRepository.saveAndFlush(cinemaSeat);
		return cinemaSeatRepository.findById(cinemaSeat.getCinemaSeatId()).get();
	}

	@Override
	public CinemaSeat viewCinemaSeat(int cinemaSeatId) {
		return cinemaSeatRepository.findById(cinemaSeatId).get();
	}

}
