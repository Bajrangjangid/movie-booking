package com.publicis.sapient.repository;

import com.publicis.sapient.entity.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Integer> {

    @Query("select s from ShowSeat s where s.booking.bookingId = :id")
    List<ShowSeat> getAllByBookingId(@Param("id") int id);

}
