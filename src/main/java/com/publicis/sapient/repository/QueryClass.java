package com.publicis.sapient.repository;

import java.util.List;
import javax.persistence.*;

import com.publicis.sapient.entity.CinemaSeat;
import com.publicis.sapient.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.publicis.sapient.entity.Booking;
import com.publicis.sapient.entity.User;

@Repository
public class QueryClass {

	@PersistenceContext
	EntityManager eManager;

	public User findByUserName(String username) throws ApiException {
		TypedQuery<User> qry = eManager.createQuery("select u from User u where u.username like :name", User.class);
		qry.setParameter("name", username);
		List<User> user = qry.getResultList();
		if (user.size() == 0)
			throw new ApiException(HttpStatus.NOT_FOUND,"User Not Available !!");
		return user.get(0);
	}

	public CinemaSeat findByHallAndSeat(Integer cinemaHallId, String seatNumber) throws ApiException {
		TypedQuery<CinemaSeat> qry = eManager.createQuery("select cs from CinemaSeat cs where cs.seatNumber = :seatNumber and cs.cinemaHall.cinemaHallId = :cinemaHallId", CinemaSeat.class);
		qry.setParameter("seatNumber", seatNumber);
		qry.setParameter("cinemaHallId", cinemaHallId);
		List<CinemaSeat> cinemaSeats = qry.getResultList();
		if (cinemaSeats.size() == 0)
			throw new ApiException(HttpStatus.NOT_FOUND,"Wrong seat number !!");
		return cinemaSeats.get(0);
	}

}
