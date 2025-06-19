package com.gstech.reservationSystem.DTO;

import com.gstech.reservationSystem.enums.ReservationStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservationDTO(

        @NotNull(message = "A data é obrigatória")
        @FutureOrPresent(message = "A data da reserva deve estar no futuro")
        LocalDateTime dateTime,
        ReservationStatus status
) {
}
