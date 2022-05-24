package com.publicis.sapient.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.publicis.sapient.domain.SeatType;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "cinema_seat")
@DynamicUpdate
public class CinemaSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cinemaSeatId;

    private String seatNumber;

    private Integer price;

    @Enumerated(EnumType.STRING)
    private SeatType type;

    @JsonIgnore
    @ManyToOne
    private CinemaHall cinemaHall;

    public CinemaSeat() {
    }

    public CinemaSeat(int cinemaSeatId, String seatNumber, Integer price, SeatType type, CinemaHall cinemaHall) {
        this.cinemaSeatId = cinemaSeatId;
        this.seatNumber = seatNumber;
        this.price = price;
        this.type = type;
        this.cinemaHall = cinemaHall;
    }

    public int getCinemaSeatId() {
        return cinemaSeatId;
    }

    public void setCinemaSeatId(int cinemaSeatId) {
        this.cinemaSeatId = cinemaSeatId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public SeatType getType() {
        return type;
    }

    public void setType(SeatType type) {
        this.type = type;
    }

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
    }
}
