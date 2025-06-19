package com.gstech.reservationSystem.controllers;

import com.gstech.reservationSystem.DTO.CreateReservationDTO;
import com.gstech.reservationSystem.DTO.ReservationDTO;
import com.gstech.reservationSystem.DTO.ResponseDTO;
import com.gstech.reservationSystem.services.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping(value = "/reservations")
@Tag(name = "Reservas", description = "Gerenciamento de reservas de mesas")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    @Operation(summary = "Listar reservas", description = "Retorna todas as reservas do usuário logado.")
    public ResponseEntity<List<ReservationDTO>> findReservations(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {

        List<ReservationDTO> reservations = reservationService.findAll(user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(reservations);
    }

    @PostMapping(value = "/new")
    @Operation(summary = "Criar reserva", description = "Cria uma nova reserva.")
    public ResponseEntity<ResponseDTO> createReservation(@RequestBody @Valid CreateReservationDTO data,
                                                  @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {
        reservationService.createReservation(data, user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("reservation completed"));
    }

    @PutMapping(value = "/cancel/{id}")
    @Operation(summary = "Cancelar reserva", description = "Cancela uma reserva existente.")
    public ResponseEntity<ResponseDTO> cancelReservation(@PathVariable Long id,
                                                         @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {
        reservationService.cancelReservation(id, user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("reservation cancelled"));
    }
}
