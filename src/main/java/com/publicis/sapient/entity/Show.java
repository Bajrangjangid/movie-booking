package com.publicis.sapient.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.publicis.sapient.domain.ShowName;
import com.publicis.sapient.domain.ShowStatus;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "shhow")
@DynamicUpdate
public class Show {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int showId;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate showDate;

	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@JsonSerialize(using = LocalTimeSerializer.class)
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime showStartTime;

	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@JsonSerialize(using = LocalTimeSerializer.class)
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime showEndTime;

	@Enumerated(EnumType.STRING)
	private ShowName showName;

	@Enumerated(EnumType.STRING)
	private ShowStatus showStatus;

	@JsonIgnore
	@OneToMany(mappedBy = "show",cascade = CascadeType.ALL)
	private List<Booking> booking;

	@OneToMany(mappedBy = "show",cascade = CascadeType.ALL)
	private List<ShowSeat> showSeats;

	@ManyToOne
	private Movie movie;

	@ManyToOne
	private CinemaHall cinemaHall;

	public Show() {
	}

	public Show(int showId, LocalDate showDate, LocalTime showStartTime, LocalTime showEndTime, ShowName showName, ShowStatus showStatus, List<Booking> booking, List<ShowSeat> showSeats, Movie movie, CinemaHall cinemaHall) {
		this.showId = showId;
		this.showDate = showDate;
		this.showStartTime = showStartTime;
		this.showEndTime = showEndTime;
		this.showName = showName;
		this.showStatus = showStatus;
		this.booking = booking;
		this.showSeats = showSeats;
		this.movie = movie;
		this.cinemaHall = cinemaHall;
	}

	public int getShowId() {
		return showId;
	}

	public void setShowId(int showId) {
		this.showId = showId;
	}

	public LocalDate getShowDate() {
		return showDate;
	}

	public void setShowDate(LocalDate showDate) {
		this.showDate = showDate;
	}

	public LocalTime getShowStartTime() {
		return showStartTime;
	}

	public void setShowStartTime(LocalTime showStartTime) {
		this.showStartTime = showStartTime;
	}

	public LocalTime getShowEndTime() {
		return showEndTime;
	}

	public void setShowEndTime(LocalTime showEndTime) {
		this.showEndTime = showEndTime;
	}

	public ShowName getShowName() { return showName; }

	public void setShowName(ShowName showName) {
		this.showName = showName;
	}

	public ShowStatus getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(ShowStatus showStatus) {
		this.showStatus = showStatus;
	}

	public List<Booking> getBooking() {
		return booking;
	}

	public void setBooking(List<Booking> booking) {
		this.booking = booking;
	}

	public List<ShowSeat> getShowSeats() {
		return showSeats;
	}

	public void setShowSeats(List<ShowSeat> showSeats) {
		this.showSeats = showSeats;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public CinemaHall getCinemaHall() {
		return cinemaHall;
	}

	public void setCinemaHall(CinemaHall cinemaHall) {
		this.cinemaHall = cinemaHall;
	}
}
