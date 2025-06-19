package com.gstech.reservationSystem.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationDTO(

        @NotBlank(message = "Name obrigatório")
        String name,

        @NotBlank(message = "Email obrigatório")
        @Email(message = "Formato de email inválido")
        String email,

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String password
) {}
