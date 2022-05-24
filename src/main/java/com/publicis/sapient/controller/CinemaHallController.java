package com.publicis.sapient.controller;

import com.publicis.sapient.constant.Constants;
import com.publicis.sapient.entity.CinemaHall;
import com.publicis.sapient.exception.ApiException;
import com.publicis.sapient.service.CinemaHallService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CinemaHallController {

	Logger logger = LoggerFactory.getLogger(CinemaHallController.class);

	@Autowired
	private CinemaHallService cinemaHallService;

	@Autowired
	LoginController loginController;

	@PostMapping("/cinemaHall")
	public ResponseEntity<CinemaHall> addCinemaHall(@RequestParam(required = false) Integer cinemaId,@RequestBody CinemaHall cinemaHall)
			throws ApiException {

		try {
			if (cinemaHall == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			cinemaHallService.addCinemaHall(cinemaHall,cinemaId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(cinemaHall, HttpStatus.CREATED);
	}

	@DeleteMapping("/cinemaHall/{cinemaHallId}")
	public ResponseEntity<CinemaHall> removeCinemaHall(@PathVariable int cinemaHallId) throws ApiException {
		CinemaHall cinemaHall = null;
		try {
			cinemaHall = cinemaHallService.removeCinemaHall(cinemaHallId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(cinemaHall, HttpStatus.ACCEPTED);
	}


	@PutMapping("/cinemaHall/{cinemaHallId}")
	public ResponseEntity<CinemaHall> updateCinemaHall(@PathVariable int cinemaHallId,@RequestParam(required = false) Integer cinemaId,@RequestBody CinemaHall cinemaHall)
			throws ApiException {
		try {
			if (cinemaHall == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			cinemaHall.setCinemaHallId(cinemaHallId);
			cinemaHall = cinemaHallService.updateCinemaHall(cinemaHall,cinemaId);

		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(cinemaHall, HttpStatus.ACCEPTED);
	}


	@GetMapping("/cinemaHalls")
	public ResponseEntity<List<CinemaHall>> viewCinemaHallList() throws ApiException {
		List<CinemaHall> cinemaHalls = null;
		try {
			cinemaHalls = cinemaHallService.viewCinemaHallList();
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(cinemaHalls, HttpStatus.OK);
	}


	@GetMapping("/cinemaHall/{cinemaHallId}")
	public ResponseEntity<CinemaHall> viewACinemaHall(@PathVariable int cinemaHallId) throws ApiException {
		CinemaHall movie = null;
		try {
			movie = cinemaHallService.viewCinemaHall(cinemaHallId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return ResponseEntity.ok(movie);
	}
}
