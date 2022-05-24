package com.publicis.sapient.controller;

import com.publicis.sapient.constant.Constants;
import com.publicis.sapient.entity.Show;
import com.publicis.sapient.exception.ApiException;
import com.publicis.sapient.service.ShowService;
import com.publicis.sapient.utility.MovieUtility;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ShowController {

	Logger logger = LoggerFactory.getLogger(ShowController.class);

	@Autowired
	private ShowService showService;

	@PostMapping("/show")
	public ResponseEntity<Show> addShow(@RequestBody Show show, @RequestParam(required = false) Integer cinemaHallId,
										@RequestParam(required = false) Integer movieId) throws ApiException {
		try {
			showService.addShow(show, cinemaHallId, movieId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(show, HttpStatus.CREATED);

	}

	@DeleteMapping("/show/{showId}")
	public ResponseEntity<Show> removeShow(@PathVariable int showId) throws ApiException {
		Show show = null;
		try {
			show = showService.viewShow(showId);
			if (show == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			showService.removeShow(showId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(show, HttpStatus.ACCEPTED);
	}


	@PutMapping("/show")
	public ResponseEntity<Show> updateShow(@RequestBody Show show,  @RequestParam(required = false) Integer cinemaHallId,
										   @RequestParam(required = false) Integer movieId) throws ApiException  {
		try {
			if (show == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			showService.updateShow(show, cinemaHallId, movieId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(show, HttpStatus.ACCEPTED);
	}

	@GetMapping("/show/{showId}")
	public ResponseEntity<Show> viewShow(@PathVariable int showId) throws  ApiException {
		Show show = null;
		try {
			show = showService.viewShow(showId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(show, HttpStatus.OK);
	}

	@GetMapping("/shows")
	public ResponseEntity<List<Show>> viewShowList() throws ApiException{
		List<Show> shows = null;
		try {
			shows = showService.viewAllShows();
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return ResponseEntity.ok(shows);
	}

	@GetMapping("/show/cinemaHall/{cinemaHallId}")
	public ResponseEntity<List<Show>> viewShowByTheatreId(@PathVariable int cinemaHallId) throws ApiException{
		List<Show> shows = null;
		try {
			shows = showService.viewShowList(cinemaHallId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return ResponseEntity.ok(shows);
	}

	@GetMapping("/showByDate/{date}")
	public ResponseEntity<List<Show>> viewShowByLocalDate(@PathVariable String date) throws ApiException {
		List<Show> shows = null;
		try {
			LocalDate filterDate = MovieUtility.convertStringToLocalDate(date);
			shows = showService.viewShowListByDate(filterDate);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return ResponseEntity.ok(shows);
	}
}
