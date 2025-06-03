package com.gstech.reservationSystem.services;

import com.gstech.reservationSystem.DTO.RestaurantTableDTO;
import com.gstech.reservationSystem.orm.RestaurantTable;
import com.gstech.reservationSystem.repositories.RestaurantTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantTableService {

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;


    public List<RestaurantTableDTO> findAllTablesAvailable() {

        List<RestaurantTable> listAvailable = restaurantTableRepository.allAvailableTables();

        return listAvailable.stream()
                .map(table -> new RestaurantTableDTO(
                        table.getId(),
                        table.getName(),
                        table.getCapacity()
                ))
                .collect(Collectors.toList());
    }
}
