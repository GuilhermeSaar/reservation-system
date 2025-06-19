package com.gstech.reservationSystem.controllers;

import com.gstech.reservationSystem.DTO.ResponseDTO;
import com.gstech.reservationSystem.DTO.RestaurantTableDTO;
import com.gstech.reservationSystem.services.RestaurantTableService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping(value = "/tables")
@Tag(name = "Mesas", description = "Gerenciamento de mesas do restaurante")
public class RestaurantTableController {

    @Autowired
    private RestaurantTableService restaurantTableService;

    @GetMapping()
    @Operation(summary = "Listar mesas", description = "Retorna todas as mesas disponíveis.")
    public ResponseEntity<List<RestaurantTableDTO>> getAllAvailableRestaurantTables() {

        List<RestaurantTableDTO> tables = restaurantTableService.findAllTables();
        return ResponseEntity.ok().body(tables);
    }

    @PostMapping(value = "/new")
    @Operation(summary = "Criar nova mesa", security = @SecurityRequirement(name = "bearerAuth"), description = "Cria uma nova mesa no restaurante.")
    public ResponseEntity<ResponseDTO> createRestaurantTable(@RequestBody RestaurantTableDTO data,
                                                             @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {

        restaurantTableService.createNewTable(data, user.getUsername());
        return ResponseEntity.ok().body(new ResponseDTO("Data has been created successfully"));
    }

    @PutMapping(value = "/delete/{id}")
    @Operation(summary = "Excluir mesa", security = @SecurityRequirement(name = "bearerAuth"), description = "Remove uma mesa pelo ID.")
    public ResponseEntity<ResponseDTO> deleteRestaurantTable(@PathVariable Long id,
                                                             @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {

        restaurantTableService.deleteTable(id, user.getUsername());
        return ResponseEntity.ok().body(new ResponseDTO("Data has been deleted successfully"));
    }

    @PutMapping(value = "/update/{id}")
    @Operation(summary = "Atualizar mesa", security = @SecurityRequirement(name = "bearerAuth"), description = "Atualiza os dados de uma mesa existente.")
    public ResponseEntity<ResponseDTO> updateRestaurantTable(@PathVariable Long id,
                                                             @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {

        restaurantTableService.updateTable(id, user.getUsername());
        return ResponseEntity.ok().body(new ResponseDTO("Data has been update successfully"));
    }
}

