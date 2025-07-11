package com.gstech.reservationSystem.enums;

public enum UserRole {

    ADMIN("ADMIN"),
    CUSTOMER("CUSTOMER");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
