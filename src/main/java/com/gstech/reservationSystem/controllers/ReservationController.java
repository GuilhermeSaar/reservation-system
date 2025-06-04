package com.gstech.reservationSystem.controllers;

import com.gstech.reservationSystem.DTO.CreateReservationDTO;
import com.gstech.reservationSystem.DTO.ResponseDTO;
import com.gstech.reservationSystem.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ResponseDTO> createReservation(@RequestBody CreateReservationDTO data,
                                                  @AuthenticationPrincipal UserDetails user) {
        reservationService.createReservation(data, user.getUsername());
        System.out.println("Usuário autenticado: " + user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("reservation completed"));

    }


}
