package com.publicis.sapient.domain;

public enum PaymentMethod {
    ONLINE("Online"), OFFLINE("Offline");

    private String status;

    private PaymentMethod(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
