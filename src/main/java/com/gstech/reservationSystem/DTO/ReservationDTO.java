package com.gstech.reservationSystem.DTO;

import com.gstech.reservationSystem.enums.ReservationStatus;

import java.time.LocalDateTime;

public record ReservationDTO(
        LocalDateTime dateTime,
        ReservationStatus status
) {
}
