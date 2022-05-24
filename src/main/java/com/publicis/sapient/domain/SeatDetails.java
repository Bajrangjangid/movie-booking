package com.publicis.sapient.domain;

public class SeatDetails {

    private int cinemaSeatId;

    private String seatNumber;

    private Integer actualPrice;

    private Double afterDiscount;

    private Integer discountPer;

    private SeatType type;

    private SeatStatus seatStatus;

    public SeatDetails() {
    }

    public SeatDetails(int cinemaSeatId, String seatNumber, Integer actualPrice, Double afterDiscount, Integer discountPer, SeatType type, SeatStatus seatStatus) {
        this.cinemaSeatId = cinemaSeatId;
        this.seatNumber = seatNumber;
        this.actualPrice = actualPrice;
        this.afterDiscount = afterDiscount;
        this.discountPer = discountPer;
        this.type = type;
        this.seatStatus = seatStatus;
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

    public Integer getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Integer actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Double getAfterDiscount() {
        return afterDiscount;
    }

    public void setAfterDiscount(Double afterDiscount) {
        this.afterDiscount = afterDiscount;
    }

    public Integer getDiscountPer() {
        return discountPer;
    }

    public void setDiscountPer(Integer discountPer) {
        this.discountPer = discountPer;
    }

    public SeatType getType() {
        return type;
    }

    public void setType(SeatType type) {
        this.type = type;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }
}
