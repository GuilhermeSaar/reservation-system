package com.gstech.reservationSystem.controllers;

import com.gstech.reservationSystem.DTO.CreateReservationDTO;
import com.gstech.reservationSystem.DTO.ResponseDTO;
import com.gstech.reservationSystem.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping(value = "/new")
    public ResponseEntity<ResponseDTO> createReservation(@RequestBody CreateReservationDTO data,
                                                  @AuthenticationPrincipal UserDetails user) {
        reservationService.createReservation(data, user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("reservation completed"));
    }

    @PutMapping(value = "/cancel/{id}")
    public ResponseEntity<ResponseDTO> cancelReservation(@PathVariable Long id,
                                                         @AuthenticationPrincipal UserDetails user) {
        reservationService.cancelReservation(id, user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("reservation cancelled"));
    }
}
