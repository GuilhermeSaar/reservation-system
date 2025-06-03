package com.gstech.reservationSystem.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationDTO(
        @NotBlank String name,
        @Email String email,
        @Size(min=6) String password
) {}
