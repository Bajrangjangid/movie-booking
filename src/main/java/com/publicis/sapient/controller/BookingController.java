package com.publicis.sapient.controller;

import com.publicis.sapient.constant.Constants;
import com.publicis.sapient.domain.BookingDetails;
import com.publicis.sapient.entity.Booking;
import com.publicis.sapient.exception.ApiException;
import com.publicis.sapient.service.BookingService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingController {

	Logger logger = LoggerFactory.getLogger(BookingController.class);

	@Autowired
	private BookingService bookingService;

	@Autowired
	LoginController loginController;

	@PostMapping(value = "/booking", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Booking> addTicketBooking(@RequestBody Booking t,
													@RequestParam(required = false) Integer customerId,
													@RequestParam(required = false) Integer showId)
			throws ApiException {
		Booking booking = null;
		try {
			booking = bookingService.addBooking(t,customerId,showId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(booking, HttpStatus.CREATED);
	}

	@GetMapping("/bookings")
	public ResponseEntity<List<Booking>> viewAllBookings() throws ApiException {
		/*
		 * if (!loginController.loginStatus()) throw new
		 * AccessForbiddenException("Not Logged In/Invalid Loggin");
		 */
		logger.info("-------List Of Bookings Fetched Successfully---------");
		List<Booking> bookings = null;
		try {
			bookings = bookingService.viewBookingList();
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return ResponseEntity.ok(bookings);
	}

	@PutMapping(value = "/booking/{bookingId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Booking> updateTicketBooking(@PathVariable int bookingId,
													   @RequestParam(required = false) Integer customerId,
													   @RequestParam(required = false) Integer showId,
													   @RequestBody Booking booking) throws ApiException {
		try {
			booking.setBookingId(bookingId);
			bookingService.updateBooking(booking,customerId,showId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(booking, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("booking/{bookingId}")
	public ResponseEntity<Booking> deleteTicketBookingById(@PathVariable int bookingId) throws ApiException {
		Booking booking = null;
		try {
			booking = bookingService.cancelBooking(bookingId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(booking, HttpStatus.ACCEPTED);
	}

	@GetMapping("/booking/{bookingId}")
	public ResponseEntity<Booking> viewBooking(@PathVariable int bookingId) throws ApiException {
		Booking booking = null;
		try {
			booking = bookingService.viewBooking(bookingId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(booking, HttpStatus.OK);
	}

	@PostMapping(value = "/bookSeats", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Booking> seatBook(@RequestParam(required = true) Integer customerId,
											@RequestParam(required = true) Integer showId,
											@RequestBody List<String> seatNumberList
	)
			throws ApiException {
		Booking booking = null;
		try {
			booking = bookingService.bookSeats(seatNumberList,customerId,showId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(booking, HttpStatus.CREATED);
	}

	@GetMapping("/bookedSeat/{bookingId}")
	public ResponseEntity<BookingDetails> getBookedSeat(@PathVariable int bookingId) throws ApiException {
		BookingDetails booking = null;
		try {
			booking = bookingService.getBookedSeatDetails(bookingId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(booking, HttpStatus.OK);
	}
}
