package com.publicis.sapient.domain;

public enum SeatType {

    PREMIUM("Premium"), GOLD("Gold"), SILVER("Silver"), REGULAR("Regular");

    private String status;

    private SeatType(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
