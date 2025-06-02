package com.gstech.reservationSystem.enums;

public enum TableStatus {

    AVAILABLE("AVAILABLE"),
    RESERVED("RESERVED"),
    INACTIVE("INACTIVE");

    private final String status;

    TableStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}