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

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTService jwtService;

    @PostMapping()
    public ResponseEntity<ResponseDTO> login(@RequestBody AuthenticationDTO data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = this.jwtService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok().body(new ResponseDTO(token));
    }
}
