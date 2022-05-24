package com.publicis.sapient.service;

import com.publicis.sapient.domain.SeatStatus;
import com.publicis.sapient.entity.Booking;
import com.publicis.sapient.entity.CinemaSeat;
import com.publicis.sapient.entity.Show;
import com.publicis.sapient.entity.ShowSeat;
import com.publicis.sapient.exception.ApiException;
import com.publicis.sapient.repository.BookingRepository;
import com.publicis.sapient.repository.CinemaSeatRepository;
import com.publicis.sapient.repository.ShowRepository;
import com.publicis.sapient.repository.ShowSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowSeatServiceImpl implements ShowSeatService {

	@Autowired
	private ShowSeatRepository showSeatRepository;

	@Autowired
	private CinemaSeatRepository cinemaSeatRepository;

	@Autowired
	private ShowRepository showRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Override
	public List<ShowSeat> addShowSeat(List<ShowSeat> seats,Integer cinemaSeatId,Integer showId,Integer bookingId) throws ApiException {
		CinemaSeat cinemaSeat = new CinemaSeat();
		Show show = new Show();
		Booking booking = new Booking();
		if (cinemaSeatId != null) {
			cinemaSeat = cinemaSeatRepository.getOne(cinemaSeatId);
		}
		if (showId != null) {
			show = showRepository.getOne(showId);
		}

		if (bookingId != null) {
			booking = bookingRepository.getOne(bookingId);
		}

		for (ShowSeat showSeat : seats) {
			showSeat.setShow(show);
			showSeat.setCinemaSeat(cinemaSeat);
			showSeat.setBooking(booking);
			showSeat.setSeatStatus(SeatStatus.BLOCKED);
		}
		return showSeatRepository.saveAll(seats);
	}

	@Override
	public List<ShowSeat> viewShowSeatList() throws ApiException {
		List<ShowSeat> li = showSeatRepository.findAll();
		if (li.size() == 0) throw new ApiException("No seats found");
		return li;
	}

	@Override
	public ShowSeat updateShowSeat(ShowSeat seat,Integer cinemaSeatId,Integer showId,Integer bookingId) {
		CinemaSeat cinemaSeat = new CinemaSeat();
		Show show = new Show();
		Booking booking = new Booking();
		if (cinemaSeatId != null) {
			cinemaSeat = cinemaSeatRepository.getOne(cinemaSeatId);
		}
		if (showId != null) {
			show = showRepository.getOne(showId);
		}

		if (bookingId != null) {
			booking = bookingRepository.getOne(bookingId);
		}
		seat.setShow(show);
		seat.setCinemaSeat(cinemaSeat);
		seat.setBooking(booking);

		return showSeatRepository.saveAndFlush(seat);
	}

	@Override
	public List<ShowSeat> cancelShowSeatBooking(List<ShowSeat> seats) {
		seats.forEach(seat -> seat.setSeatStatus(SeatStatus.CANCELLED));
		return showSeatRepository.saveAll(seats);
	}

}
