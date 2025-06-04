package com.gstech.reservationSystem.DTO;

import java.time.LocalDateTime;

public record CreateReservationDTO(
        Long tableId,
        LocalDateTime dateTime
) {}
