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
	public CinemaSeat addCinemaSeat(CinemaSeat cinemaSeat,Integer cinemaHallId) throws ApiException {
		CinemaHall cinemaHall = cinemaHallRepository.findById(cinemaHallId).get();
		cinemaSeat.setCinemaHall(cinemaHall);

		cinemaSeatRepository.saveAndFlush(cinemaSeat);
		return cinemaSeat;
	}

	@Override
	public List<CinemaSeat> viewCinemaSeatList() {
		return cinemaSeatRepository.findAll();
	}

	@Override
	public CinemaSeat removeCinemaSeat(int cinemaSeatId) {
		CinemaSeat c = cinemaSeatRepository.getOne(cinemaSeatId);
		cinemaSeatRepository.delete(c);
		return c;
	}

	@Override
	public CinemaSeat updateCinemaSeat(CinemaSeat cinemaSeat,Integer cinemaHallId) throws ApiException {

		CinemaHall cinemaHall = cinemaHallRepository.findById(cinemaHallId).get();
		cinemaSeat.setCinemaHall(cinemaHall);

		CinemaSeat cu = cinemaSeatRepository.getOne(cinemaSeat.getCinemaSeatId());
		if(cu.getCinemaSeatId() == 0) {
			throw new ApiException(HttpStatus.NOT_FOUND,"Cinema seat not available");
		}
		cinemaSeatRepository.saveAndFlush(cinemaSeat);
		return cinemaSeatRepository.getOne(cinemaSeat.getCinemaSeatId());
	}

	@Override
	public CinemaSeat viewCinemaSeat(int cinemaSeatId) {
		return cinemaSeatRepository.findById(cinemaSeatId).get();
	}

}
