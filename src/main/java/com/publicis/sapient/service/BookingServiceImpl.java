package com.publicis.sapient.service;

import com.publicis.sapient.domain.*;
import com.publicis.sapient.entity.*;
import com.publicis.sapient.exception.ApiException;
import com.publicis.sapient.repository.BookingRepository;
import com.publicis.sapient.repository.QueryClass;
import com.publicis.sapient.repository.ShowSeatRepository;
import com.publicis.sapient.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ShowService showService;

	@Autowired
	QueryClass queryClass;

	@Autowired
	ShowSeatRepository showSeatRepository;

	@Override
	public Booking addBooking(Booking booking,Integer userId,Integer showId) throws ApiException {
		return saveBookingDetails(booking,userId,showId);
	}

	@Override
	public Booking updateBooking(Booking booking,Integer userId,Integer showId) throws ApiException {
		return saveBookingDetails(booking,userId,showId);
	}

	@Override
	public Booking cancelBooking(int bookingId) throws ApiException {
		Booking b = bookingRepository.getOne(bookingId);
		bookingRepository.delete(b);
		return b;
	}

	@Override
	public List<Booking> viewBookingList() throws ApiException {
		List<Booking> bk = bookingRepository.findAll();
		return bk;
	}

	@Override
	public Booking viewBooking(int bookingId) throws ApiException {
		return bookingRepository.findById(bookingId).get();
	}

	@Override
	@Transactional
	public Booking bookSeats(List<String> seatNumberList, Integer userId, Integer showId) throws ApiException {
		// Add Booking
		Booking booking = new Booking();
		booking.setBookingDate(LocalDate.now());
		booking.setNumberOfSeat(seatNumberList.size());
		booking.setStatus(BookingStatus.RESERVED);
		this.saveBookingDetails(booking,userId,showId);

		Show show = booking.getShow();

		// Add Seats with booking id
		Integer cinemaHallId = show.getCinemaHall().getCinemaHallId();
		List<ShowSeat> showSeatList = new ArrayList<>();
		Integer ticketNo = 0;
		for (String seatNumber : seatNumberList) {
			CinemaSeat cinemaSeat = queryClass.findByHallAndSeat(cinemaHallId,seatNumber);

			ticketNo++;

			ShowSeat showSeat = new ShowSeat();

			// Calculate discount price
			this.calculateDiscountPrice(ticketNo,cinemaSeat,show,showSeat);

			showSeat.setSeatStatus(SeatStatus.BLOCKED);
			showSeat.setBooking(booking);
			showSeat.setCinemaSeat(cinemaSeat);
			showSeat.setShow(show);
			showSeatList.add(showSeat);
		}

		// Book all seats
		if(showSeatList.size() > 0) {
			showSeatRepository.saveAll(showSeatList);
		}

		return booking;
	}

	@Override
	public BookingDetails getBookedSeatDetails(int bookingId) throws ApiException {
		BookingDetails bookingDetails = new BookingDetails();
		Booking booking = this.viewBooking(bookingId);
		bookingDetails.setBookingDate(booking.getBookingDate());
		bookingDetails.setBookingId(bookingId);
		bookingDetails.setNumberOfSeat(booking.getNumberOfSeat());
		bookingDetails.setStatus(booking.getStatus());

		// User Details
		bookingDetails.setUser(booking.getUser());

		// Show and Movie Details
		if(booking.getShow() != null) {
			bookingDetails.setShowDate(booking.getShow().getShowDate());
			bookingDetails.setShowStartTime(booking.getShow().getShowStartTime());
			bookingDetails.setShowEndTime(booking.getShow().getShowEndTime());
			bookingDetails.setMovie(booking.getShow().getMovie());
		}
		List<SeatDetails> seatDetailList = new ArrayList<>();
		List<ShowSeat> showSeatList = showSeatRepository.getAllByBookingId(bookingId);
		Double totalAmount = 0.0;
		for (ShowSeat showSeat : showSeatList) {
			SeatDetails seatDetail = new SeatDetails();
			seatDetail.setSeatStatus(showSeat.getSeatStatus());
			seatDetail.setAfterDiscount(showSeat.getPriceAfterDiscount());
			seatDetail.setDiscountPer(showSeat.getDiscountPer());
			if(showSeat.getCinemaSeat() != null) {
				seatDetail.setActualPrice(showSeat.getCinemaSeat().getPrice());
				seatDetail.setCinemaSeatId(showSeat.getCinemaSeat().getCinemaSeatId());
				seatDetail.setSeatNumber(showSeat.getCinemaSeat().getSeatNumber());
				seatDetail.setType(showSeat.getCinemaSeat().getType());
			}

			if(showSeat.getSeatStatus() == SeatStatus.BLOCKED || showSeat.getSeatStatus() == SeatStatus.BOOKED) {
				totalAmount += showSeat.getPriceAfterDiscount();
			}
			seatDetailList.add(seatDetail);
		}
		bookingDetails.setSeatDetails(seatDetailList);
		bookingDetails.setTotalAmount(totalAmount);
		return bookingDetails;
	}

	private void calculateDiscountPrice(Integer ticketNo, CinemaSeat cinemaSeat,Show show,ShowSeat showSeat) {
		double ticketPrice = cinemaSeat.getPrice();
		int discount = 0;

		// 50 % Discount on third ticket
		if (ticketNo == 3) {
			discount = 50;
		}

		// 20 % discount on Afternoon Show
		if (show.getShowName() == ShowName.AFTERNOON) {
			discount = discount + 20;
		}

		if (discount > 0){
			double discountPrice = (ticketPrice * discount) / 100;
			ticketPrice = ticketPrice - discountPrice;
		}

		showSeat.setPriceAfterDiscount(ticketPrice);
		showSeat.setDiscountPer(discount);

	}

	private Booking saveBookingDetails(Booking booking,Integer userId,Integer showId) throws ApiException {
		if (userId != null) {
			User user = userRepository.getOne(userId);
			booking.setUser(user);
		}
		if (showId != null) {
			Show show = showService.viewShow(showId);
			booking.setShow(show);
		}

		bookingRepository.saveAndFlush(booking);
		return booking;
	}
}
