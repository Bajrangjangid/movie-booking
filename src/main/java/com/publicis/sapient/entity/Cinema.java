package com.publicis.sapient.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cinema")
@DynamicUpdate
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cinemaId;

    private String name;

    private Integer totalCinemaHalls;

    @ManyToOne
    private City city;

    @JsonIgnore
    @OneToMany(mappedBy = "cinema",cascade = CascadeType.ALL)
    private List<CinemaHall> cinemaHalls;

    public Cinema() {
    }

    public Cinema(int cinemaId, String name, Integer totalCinemaHalls, City city, List<CinemaHall> cinemaHalls) {
        this.cinemaId = cinemaId;
        this.name = name;
        this.totalCinemaHalls = totalCinemaHalls;
        this.city = city;
        this.cinemaHalls = cinemaHalls;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalCinemaHalls() {
        return totalCinemaHalls;
    }

    public void setTotalCinemaHalls(Integer totalCinemaHalls) {
        this.totalCinemaHalls = totalCinemaHalls;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<CinemaHall> getCinemaHalls() {
        return cinemaHalls;
    }

    public void setCinemaHalls(List<CinemaHall> cinemaHalls) {
        this.cinemaHalls = cinemaHalls;
    }
}

