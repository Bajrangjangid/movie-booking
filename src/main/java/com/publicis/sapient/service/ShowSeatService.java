package com.publicis.sapient.service;

import com.publicis.sapient.entity.ShowSeat;
import com.publicis.sapient.exception.ApiException;

import java.util.List;

public interface ShowSeatService {

	List<ShowSeat> addShowSeat(List<ShowSeat> seats,Integer cinemaSeatId,Integer showId,Integer bookingId) throws ApiException;

	List<ShowSeat> viewShowSeatList() throws ApiException;

	ShowSeat updateShowSeat(ShowSeat seat,Integer cinemaSeatId,Integer showId,Integer bookingId) throws ApiException;

	List<ShowSeat> cancelShowSeatBooking(List<ShowSeat> seats) throws ApiException;

}
