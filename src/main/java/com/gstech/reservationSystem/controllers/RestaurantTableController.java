package com.gstech.reservationSystem.controllers;

import com.gstech.reservationSystem.DTO.RestaurantTableDTO;
import com.gstech.reservationSystem.services.RestaurantTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/tables")
public class RestaurantTableController {

    @Autowired
    private RestaurantTableService restaurantTableService;

    @GetMapping()
    public ResponseEntity<List<RestaurantTableDTO>> getAllAvailableRestaurantTables() {

        List<RestaurantTableDTO> tables = restaurantTableService.findAllTablesAvailable();
        return ResponseEntity.ok().body(tables);
    }

}
