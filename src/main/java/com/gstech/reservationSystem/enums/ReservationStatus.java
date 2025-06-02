package com.gstech.reservationSystem.enums;

public enum ReservationStatus {

    ACTIVE("ACTIVE"),
    CANCELED("CANCELED");

    private final String status;

    ReservationStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
