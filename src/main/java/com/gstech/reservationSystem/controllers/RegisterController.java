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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/register")
@Tag(name = "Registro", description = "Registro de novos usuários")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping
    @Operation(summary = "Registrar novo usuário", description = "Cria um novo usuário no sistema.")
    public ResponseEntity<ResponseDTO> register(@RequestBody @Valid UserRegistrationDTO data) {

        registerService.registerUser(data);
        return ResponseEntity.ok(new ResponseDTO("User registered successfully"));
    }
}
