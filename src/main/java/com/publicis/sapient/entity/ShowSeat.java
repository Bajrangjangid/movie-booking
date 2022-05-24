package com.publicis.sapient.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.publicis.sapient.domain.SeatStatus;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "show_seat")
@DynamicUpdate
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int showSeatId;

    private Double priceAfterDiscount;

    private Integer discountPer;

    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    @JsonIgnore
    @ManyToOne
    private Show show;

    @JsonIgnore
    @ManyToOne
    private Booking booking;

    @JsonIgnore
    @ManyToOne
    private CinemaSeat cinemaSeat;

    public ShowSeat() {
    }

    public ShowSeat(int showSeatId, Double priceAfterDiscount, Integer discountPer, SeatStatus seatStatus, Show show, Booking booking, CinemaSeat cinemaSeat) {
        this.showSeatId = showSeatId;
        this.priceAfterDiscount = priceAfterDiscount;
        this.discountPer = discountPer;
        this.seatStatus = seatStatus;
        this.show = show;
        this.booking = booking;
        this.cinemaSeat = cinemaSeat;
    }

    public int getShowSeatId() {
        return showSeatId;
    }

    public void setShowSeatId(int showSeatId) {
        this.showSeatId = showSeatId;
    }

    public Double getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public void setPriceAfterDiscount(Double priceAfterDiscount) {
        this.priceAfterDiscount = priceAfterDiscount;
    }

    public Integer getDiscountPer() {
        return discountPer;
    }

    public void setDiscountPer(Integer discountPer) {
        this.discountPer = discountPer;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public CinemaSeat getCinemaSeat() {
        return cinemaSeat;
    }

    public void setCinemaSeat(CinemaSeat cinemaSeat) {
        this.cinemaSeat = cinemaSeat;
    }
}
