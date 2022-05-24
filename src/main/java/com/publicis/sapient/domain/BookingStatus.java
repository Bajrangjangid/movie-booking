package com.publicis.sapient.domain;

public enum BookingStatus {
    RESERVED("Reserved"), BOOKED("Booked"), EXPIRED("Expired");

    private String status;

    private BookingStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
