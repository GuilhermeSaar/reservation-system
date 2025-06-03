package com.gstech.reservationSystem.controllers;

import com.gstech.reservationSystem.DTO.ResponseDTO;
import com.gstech.reservationSystem.DTO.UserRegistrationDTO;
import com.gstech.reservationSystem.services.RegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping
    public ResponseEntity<ResponseDTO> register(@RequestBody @Valid UserRegistrationDTO data) {

        registerService.registerUser(data);
        return ResponseEntity.ok(new ResponseDTO("User registered successfully"));
    }
}
