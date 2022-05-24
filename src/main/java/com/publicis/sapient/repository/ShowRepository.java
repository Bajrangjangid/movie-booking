package com.publicis.sapient.repository;

import com.publicis.sapient.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Integer> {

	@Query("select s from Show s where s.cinemaHall.cinemaHallId = :id")
	List<Show> getAllByCinemaHallId(@Param("id") int id);

}
