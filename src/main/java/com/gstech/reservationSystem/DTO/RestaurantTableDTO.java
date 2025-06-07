package com.gstech.reservationSystem.DTO;

import com.gstech.reservationSystem.enums.TableStatus;

public record RestaurantTableDTO(
        Long id,
        String name,
        int capacity,
        TableStatus status
) {}

