package com.gstech.reservationSystem.controllers;

import com.gstech.reservationSystem.DTO.AuthenticationDTO;
import com.gstech.reservationSystem.DTO.ResponseDTO;
import com.gstech.reservationSystem.orm.User;
import com.gstech.reservationSystem.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping(value = "/auth")
@Tag(name = "Autenticação", description = "Endpoints de login e geração de token")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTService jwtService;

    @PostMapping()
    @Operation(summary = "Login do usuário", description = "Autentica o usuário e retorna o token JWT. ")
    public ResponseEntity<ResponseDTO> login(@RequestBody AuthenticationDTO data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = this.jwtService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok().body(new ResponseDTO(token));
    }
}
