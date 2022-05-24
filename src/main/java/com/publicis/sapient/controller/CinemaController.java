package com.publicis.sapient.controller;

import com.publicis.sapient.constant.Constants;
import com.publicis.sapient.entity.Cinema;
import com.publicis.sapient.exception.ApiException;
import com.publicis.sapient.service.CinemaService;
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
public class CinemaController {

	Logger logger = LoggerFactory.getLogger(CinemaController.class);

	@Autowired
	CinemaService cinemaService;

	@PostMapping("/cinema")
	public ResponseEntity<Cinema> addCinema(@RequestBody Cinema cinema)
			throws ApiException {
		try {
			cinema = cinemaService.addCinema(cinema);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(cinema, HttpStatus.CREATED);
	}


	@PutMapping("/cinema/{cinemaId}")
	public ResponseEntity<Cinema> updateCinema(@PathVariable int cinemaId, @RequestBody Cinema cinema)
			throws ApiException {
		try {
			if (cinema == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			cinema.setCinemaId(cinemaId);
			cinema = cinemaService.updateCinema(cinema);

		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(cinema, HttpStatus.ACCEPTED);
	}


	@GetMapping("/cinemas")
	public ResponseEntity<List<Cinema>> viewCinemaList() throws ApiException {
		List<Cinema> cinemas = null;
		try {
			cinemas = cinemaService.viewCinemaList();
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return ResponseEntity.ok(cinemas);
	}

	@GetMapping("/cinema/{cinemaId}")
	public ResponseEntity<Cinema> viewCinema(@PathVariable int cinemaId) throws ApiException {
		Cinema cinema = null;
		try {
			cinema = cinemaService.viewCinema(cinemaId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(cinema, HttpStatus.OK);
	}


	@DeleteMapping("/cinema/{cinemaId}")
	public ResponseEntity<Cinema> removeCinema(@PathVariable int cinemaId)
			throws ApiException {
		Cinema cinema = null;
		try {
			cinema = cinemaService.viewCinema(cinemaId);
			if (cinema == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			cinemaService.removeCinema(cinemaId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(cinema, HttpStatus.OK);
	}

}