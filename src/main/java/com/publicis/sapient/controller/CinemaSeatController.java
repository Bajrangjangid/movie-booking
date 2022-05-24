package com.publicis.sapient.controller;

import com.publicis.sapient.constant.Constants;
import com.publicis.sapient.entity.CinemaSeat;
import com.publicis.sapient.exception.ApiException;
import com.publicis.sapient.service.CinemaSeatService;
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
public class CinemaSeatController {

	Logger logger = LoggerFactory.getLogger(CinemaSeatController.class);

	@Autowired
	private CinemaSeatService cinemaSeatService;

	@GetMapping("/cinemaSeats")
	public ResponseEntity<List<CinemaSeat>> getAllCinemaSeats() throws ApiException {
		List<CinemaSeat> cinemaSeat = null;
		try {
			cinemaSeat = cinemaSeatService.viewCinemaSeatList();
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(cinemaSeat, HttpStatus.OK);
	}

	@PostMapping("/cinemaSeat")
	public ResponseEntity<CinemaSeat> addCinemaSeat(@RequestParam(required = false) Integer cinemaHallId,@RequestBody CinemaSeat t)
			throws ApiException {
		CinemaSeat cinemaSeat = null;
		try {
			cinemaSeat = cinemaSeatService.addCinemaSeat(t,cinemaHallId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(cinemaSeat, HttpStatus.CREATED);
	}

	@PutMapping("/cinemaSeat/{cinemaSeatId}")
	public ResponseEntity<CinemaSeat> updateCinemaSeat(@PathVariable int cinemaSeatId,@RequestParam(required = false) Integer cinemaHallId,@RequestBody CinemaSeat cinemaSeat)
			throws  ApiException {
		try {
			cinemaSeat.setCinemaSeatId(cinemaSeatId);
			cinemaSeat = cinemaSeatService.updateCinemaSeat(cinemaSeat,cinemaHallId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(cinemaSeat, HttpStatus.ACCEPTED);
	}

	@GetMapping("/cinemaSeat/{cinemaSeatId}")
	public ResponseEntity<CinemaSeat> findCinemaSeat(@PathVariable int cinemaSeatId) throws  ApiException {
		CinemaSeat cinemaSeat = null;
		try {
			cinemaSeat = cinemaSeatService.viewCinemaSeat(cinemaSeatId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return ResponseEntity.ok(cinemaSeat);
	}

	@DeleteMapping("/cinemaSeat/{cinemaSeatId}")
	public ResponseEntity deleteMoviesById(@PathVariable int cinemaSeatId) throws ApiException {
		List<CinemaSeat> cinemaSeat = null;
		try {
			cinemaSeatService.removeCinemaSeat(cinemaSeatId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(cinemaSeat, HttpStatus.ACCEPTED);
	}

}
