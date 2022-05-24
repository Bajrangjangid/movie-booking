package com.publicis.sapient.controller;

import com.publicis.sapient.constant.Constants;
import com.publicis.sapient.entity.ShowSeat;
import com.publicis.sapient.exception.ApiException;
import com.publicis.sapient.service.ShowSeatService;
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
public class ShowSeatController {

	Logger logger = LoggerFactory.getLogger(ShowSeatController.class);

	@Autowired
	private ShowSeatService showSeatService;

	@Autowired
	LoginController loginController;

	@PostMapping("/showSeat")
	public ResponseEntity<List<ShowSeat>> addAShowSeat(@RequestBody List<ShowSeat> showSeats,
													   @RequestParam(required = false) Integer cinemaSeatId,
													   @RequestParam(required = false) Integer showId,
													   @RequestParam(required = false) Integer bookingId)
			throws ApiException {
		try {
			showSeats = showSeatService.addShowSeat(showSeats,cinemaSeatId,showId,bookingId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(showSeats, HttpStatus.CREATED);
	}

	@GetMapping("/showSeats")
	public ResponseEntity<List<ShowSeat>> viewShowSeatList() throws ApiException {
		List<ShowSeat> showSeats = null;
		try {
			showSeats = showSeatService.viewShowSeatList();
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return ResponseEntity.ok(showSeats);
	}

	@PutMapping("/showSeat")
	public ResponseEntity<ShowSeat> updateShowSeat(@PathVariable int showSeatId,
												   @RequestParam(required = false) Integer cinemaSeatId,
												   @RequestParam(required = false) Integer showId,
												   @RequestParam(required = false) Integer bookingId,
												   @RequestBody ShowSeat showSeat) throws ApiException {
		try {
			if (showSeat == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			showSeat.setShowSeatId(showSeatId);
			showSeat = showSeatService.updateShowSeat(showSeat,cinemaSeatId,showId,bookingId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(showSeat, HttpStatus.ACCEPTED);
	}

//	@PutMapping("/showSeat/book")
//	public ResponseEntity<List<ShowSeat>> bookAShowSeat(@RequestBody List<ShowSeat> showSeats) throws ApiException {
//		try {
//			showSeats = showSeatService.bookShowSeat(showSeats);
//		} catch (ApiException apiEx) {
//			throw apiEx;
//		} catch (Exception e) {
//			if (e instanceof TransactionException) {
//				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
//			}
//			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
//		}
//		return new ResponseEntity<>(showSeats, HttpStatus.ACCEPTED);
//	}
//
//	@PutMapping("/showSeat/cancel")
//	public ResponseEntity<List<ShowSeat>> cancelAShowSeat(@RequestBody List<ShowSeat> showSeat) throws ApiException {
//		try {
//			showSeat = showSeatService.cancelShowSeatBooking(showSeat);
//		} catch (ApiException apiEx) {
//			throw apiEx;
//		} catch (Exception e) {
//			if (e instanceof TransactionException) {
//				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
//			}
//			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
//		}
//		return new ResponseEntity<>(showSeat, HttpStatus.ACCEPTED);
//	}
//
//	@PutMapping("/showSeat/block")
//	public ResponseEntity<List<ShowSeat>> blockShowSeat(@RequestBody List<ShowSeat> showSeat)
//			throws ApiException {
//		try {
//			showSeat = showSeatService.blockShowSeat(showSeat);
//		} catch (ApiException apiEx) {
//			throw apiEx;
//		} catch (Exception e) {
//			if (e instanceof TransactionException) {
//				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
//			}
//			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
//		}
//		return new ResponseEntity<>(showSeat, HttpStatus.ACCEPTED);
//	}
}
