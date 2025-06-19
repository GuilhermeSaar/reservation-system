package com.gstech.reservationSystem.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Reservas de Restaurante",
                version = "1.0",
                description = "Documentação da API para autenticação, reservas e mesas de restaurante."
        )
)
public class OpenAPIConfig {
}

