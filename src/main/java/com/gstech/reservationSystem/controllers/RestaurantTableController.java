package com.gstech.reservationSystem.controllers;

import com.gstech.reservationSystem.DTO.ResponseDTO;
import com.gstech.reservationSystem.DTO.RestaurantTableDTO;
import com.gstech.reservationSystem.services.RestaurantTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tables")
public class RestaurantTableController {

    @Autowired
    private RestaurantTableService restaurantTableService;

    @GetMapping(value = "/available")
    public ResponseEntity<List<RestaurantTableDTO>> getAllAvailableRestaurantTables() {

        List<RestaurantTableDTO> tables = restaurantTableService.findAllTablesAvailable();
        return ResponseEntity.ok().body(tables);
    }

    @PostMapping(value = "/new")
    public ResponseEntity<ResponseDTO> createRestaurantTable(@RequestBody RestaurantTableDTO data) {

        restaurantTableService.createNewTable(data);
        return ResponseEntity.ok().body(new ResponseDTO("Data has been created successfully"));
    }

    @PutMapping(value = "/delete/{id}")
    public ResponseEntity<ResponseDTO> updateRestaurantTable(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {

        restaurantTableService.deleteTable(id, user.getUsername());
        return ResponseEntity.ok().body(new ResponseDTO("Data has been deleted successfully"));
    }

}

