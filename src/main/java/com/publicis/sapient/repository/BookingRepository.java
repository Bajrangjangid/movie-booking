package com.publicis.sapient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.publicis.sapient.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

}
