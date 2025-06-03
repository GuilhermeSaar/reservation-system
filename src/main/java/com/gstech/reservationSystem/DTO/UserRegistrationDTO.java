package com.gstech.reservationSystem.DTO;

import com.gstech.reservationSystem.enums.UserRole;

public record UserRegistrationDTO(String name, String email, String password, UserRole role) {
}
