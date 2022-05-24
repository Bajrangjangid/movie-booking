package com.publicis.sapient.service;

import com.publicis.sapient.entity.Payment;
import com.publicis.sapient.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {

	Payment addPayment(Payment payment,Integer bookingId) throws ApiException;

	Payment viewPayment(int paymentId) throws ApiException;

	List<Payment> viewPaymentList() throws ApiException;
}