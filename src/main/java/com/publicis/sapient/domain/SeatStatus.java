package com.publicis.sapient.domain;

public enum SeatStatus {

    BLOCKED("Blocked"), BOOKED("Booked"), AVAILABLE("Available"), CANCELLED("Cancelled");

    private String status;

    private SeatStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
