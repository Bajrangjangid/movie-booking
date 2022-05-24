package com.publicis.sapient.service;

import com.publicis.sapient.domain.BookingStatus;
import com.publicis.sapient.domain.SeatStatus;
import com.publicis.sapient.entity.Booking;
import com.publicis.sapient.entity.Payment;
import com.publicis.sapient.entity.ShowSeat;
import com.publicis.sapient.exception.ApiException;
import com.publicis.sapient.repository.PaymentRepository;
import com.publicis.sapient.repository.ShowSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private BookingService bookingService;

	@Autowired
	private ShowSeatRepository showSeatRepository;

	public PaymentServiceImpl(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}

	@Override
	public Payment addPayment(Payment payment,Integer bookingId) throws ApiException {
		Booking booking = bookingService.viewBooking(bookingId);
		if(booking.getStatus() == BookingStatus.BOOKED) {
			throw new ApiException(HttpStatus.BAD_REQUEST,"Payment completed already");
		}
		payment.setBooking(booking);

		// Update the seat status and mark it to Booked
		List<ShowSeat> showSeatList = showSeatRepository.getAllByBookingId(bookingId);
		Double totalAmount = 0.0;
		for (ShowSeat showSeat : showSeatList) {
			showSeat.setSeatStatus(SeatStatus.BOOKED);
			totalAmount += showSeat.getPriceAfterDiscount();
			showSeatRepository.saveAndFlush(showSeat);
		}

		payment.setAmount(totalAmount);
		payment.setPaymentDateTime(LocalDateTime.now());
		paymentRepository.saveAndFlush(payment);

		// Update Booking Status
		booking.setStatus(BookingStatus.BOOKED);
		bookingService.updateBooking(booking,booking.getUser().getUserId(),booking.getShow().getShowId());
		return payment;
	}

	@Override
	public List<Payment> viewPaymentList() {
		return paymentRepository.findAll();
	}

	@Override
	public Payment viewPayment(int paymentId) {
		return paymentRepository.findById(paymentId).get();
	}

}
