package com.gstech.reservationSystem.DTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateReservationDTO(
        @NotNull
        @Min(value = 1)
        Long tableId,

        @NotNull(message = "A data é obrigatória")
        @FutureOrPresent(message = "A data da reserva deve estar no futuro")
        LocalDateTime dateTime,

        @NotNull @Min(value = 1)
        Integer guestCount
) {}
