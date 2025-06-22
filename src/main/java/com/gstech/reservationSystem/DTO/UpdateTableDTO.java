package com.gstech.reservationSystem.DTO;

import com.gstech.reservationSystem.enums.TableStatus;

public record UpdateTableDTO(
   String name,
   int capacity,
   TableStatus status
) {}
