package com.publicis.sapient.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cinema_hall")
@DynamicUpdate
public class CinemaHall {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cinemaHallId;

    private String name;

    private Integer totalSeats;

    @ManyToOne
    private Cinema cinema;

    @JsonIgnore
    @OneToMany(mappedBy = "cinemaHall",cascade = CascadeType.ALL)
    private List<CinemaSeat> cinemaSeats;

    public CinemaHall() {
    }

    public CinemaHall(int cinemaHallId, String name, Integer totalSeats, Cinema cinema, List<CinemaSeat> cinemaSeats) {
        this.cinemaHallId = cinemaHallId;
        this.name = name;
        this.totalSeats = totalSeats;
        this.cinema = cinema;
        this.cinemaSeats = cinemaSeats;
    }

    public int getCinemaHallId() {
        return cinemaHallId;
    }

    public void setCinemaHallId(int cinemaHallId) {
        this.cinemaHallId = cinemaHallId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public List<CinemaSeat> getCinemaSeats() {
        return cinemaSeats;
    }

    public void setCinemaSeats(List<CinemaSeat> cinemaSeats) {
        this.cinemaSeats = cinemaSeats;
    }
}

