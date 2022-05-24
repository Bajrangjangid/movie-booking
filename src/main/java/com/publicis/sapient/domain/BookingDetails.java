package com.publicis.sapient.domain;

import com.publicis.sapient.entity.Movie;
import com.publicis.sapient.entity.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class BookingDetails {

    private int bookingId;

    private Integer numberOfSeat;

    private LocalDate bookingDate;

    private BookingStatus status;

    private LocalDate showDate;

    private LocalTime showStartTime;

    private LocalTime showEndTime;

    private Double totalAmount;

    private User user;

    private Movie movie;

    private List<SeatDetails> seatDetails;

    public BookingDetails() {
    }

    public BookingDetails(int bookingId, Integer numberOfSeat, LocalDate bookingDate, BookingStatus status,
                          LocalDate showDate, LocalTime showStartTime, LocalTime showEndTime, Double totalAmount,
                          User user, Movie movie, List<SeatDetails> seatDetails) {
        this.bookingId = bookingId;
        this.numberOfSeat = numberOfSeat;
        this.bookingDate = bookingDate;
        this.status = status;
        this.showDate = showDate;
        this.showStartTime = showStartTime;
        this.showEndTime = showEndTime;
        this.totalAmount = totalAmount;
        this.user = user;
        this.movie = movie;
        this.seatDetails = seatDetails;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getNumberOfSeat() {
        return numberOfSeat;
    }

    public void setNumberOfSeat(Integer numberOfSeat) {
        this.numberOfSeat = numberOfSeat;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
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

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<SeatDetails> getSeatDetails() {
        return seatDetails;
    }

    public void setSeatDetails(List<SeatDetails> seatDetails) {
        this.seatDetails = seatDetails;
    }
}
