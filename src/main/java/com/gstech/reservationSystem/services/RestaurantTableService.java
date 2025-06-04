package com.gstech.reservationSystem.services;

import com.gstech.reservationSystem.DTO.RestaurantTableDTO;
import com.gstech.reservationSystem.enums.TableStatus;
import com.gstech.reservationSystem.exceptions.TableNameAlreadyExistsException;
import com.gstech.reservationSystem.orm.RestaurantTable;
import com.gstech.reservationSystem.repositories.RestaurantTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantTableService {

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;

    @Transactional(readOnly = true)
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

    @Transactional
    public void createNewTable(RestaurantTableDTO data) {

        if(restaurantTableRepository.findByName(data.name()).isPresent()) {

            throw new TableNameAlreadyExistsException("The table name is already taken.");
        }

        var table = new RestaurantTable();
        table.setName(data.name());
        table.setCapacity(data.capacity());
        table.setStatus(TableStatus.INACTIVE);

        restaurantTableRepository.save(table);
    }





}
