package com.gstech.reservationSystem.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gstech.reservationSystem.DTO.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String json = new ObjectMapper().writeValueAsString(
                new ResponseDTO("Usuário não autenticado!")
        );

        response.getWriter().write(json);
    }

}
