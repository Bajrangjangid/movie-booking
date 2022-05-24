package com.publicis.sapient.service;

import com.publicis.sapient.domain.BookingDetails;
import com.publicis.sapient.entity.Booking;
import com.publicis.sapient.exception.ApiException;

import java.util.List;

public interface BookingService {
	Booking addBooking(Booking booking,Integer customerId,Integer showId) throws ApiException;

	List<Booking> viewBookingList() throws ApiException;

	Booking updateBooking(Booking booking,Integer customerId,Integer showId) throws ApiException;

	Booking cancelBooking(int bookingId) throws ApiException;

	Booking viewBooking(int bookingId) throws ApiException;

	Booking bookSeats(List<String> seatNumberList, Integer userId, Integer showId) throws ApiException;

	BookingDetails getBookedSeatDetails(int bookingId) throws ApiException;
}
