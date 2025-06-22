package com.gstech.reservationSystem.controllers;

import com.gstech.reservationSystem.DTO.ResponseDTO;
import com.gstech.reservationSystem.DTO.RestaurantTableDTO;
import com.gstech.reservationSystem.DTO.UpdateTableDTO;
import com.gstech.reservationSystem.services.TableService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
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
public class TableController {

    @Autowired
    private TableService restaurantTableService;

    @GetMapping()
    @Operation(summary = "Listar mesas", description = "Retorna todas as mesas e seus status.")
    public ResponseEntity<List<RestaurantTableDTO>> getAllAvailableRestaurantTables() {

        List<RestaurantTableDTO> tables = restaurantTableService.findAllTables();
        return ResponseEntity.ok().body(tables);
    }

    @PostMapping(value = "/new")
    @Operation(summary = "Criar nova mesa (ADMIN)", security = @SecurityRequirement(name = "bearerAuth"), description = "Cria uma nova mesa no restaurante.")
    public ResponseEntity<ResponseDTO> createRestaurantTable(@RequestBody RestaurantTableDTO data,
                                                             @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {

        if (user == null) {
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado!");
        }

        restaurantTableService.createNewTable(data, user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Mesa criada com sucesso"));
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = "Excluir mesa (ADMIN)", security = @SecurityRequirement(name = "bearerAuth"), description = "Remove uma mesa pelo ID.")
    public ResponseEntity<ResponseDTO> deleteRestaurantTable(@PathVariable Long id,
                                                             @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {

        if (user == null) {
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado!");
        }

        restaurantTableService.deleteTable(id, user.getUsername());
        return ResponseEntity.ok().body(new ResponseDTO("Mesa Deletada!"));
    }

    @PutMapping(value = "/update/{id}")
    @Operation(summary = "Atualizar mesa para disponível (ADMIN)", security = @SecurityRequirement(name = "bearerAuth"), description = "Atualiza os dados de uma mesa existente.")
    public ResponseEntity<ResponseDTO> updateRestaurantTable(@PathVariable Long id,
                                                             @RequestBody UpdateTableDTO data,
                                                             @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {

        if (user == null) {
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado!");
        }

        restaurantTableService.updateTable(id, user.getUsername(), data);
        return ResponseEntity.ok().body(new ResponseDTO("Mesa Atualizada!"));
    }
}

