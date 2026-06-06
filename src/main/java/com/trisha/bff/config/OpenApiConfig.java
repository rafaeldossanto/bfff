package com.trisha.bff.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Metadados do Swagger UI do BFF. O BFF e a porta de entrada do front, entao
 * sua documentacao OpenAPI e o contrato que o front consome — vale mante-la
 * descritiva.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bffOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Trilha BFF")
                .description("Backend for Frontend — agrega e roteia chamadas aos microservicos da Trilha.")
                .version("v1"));
    }
}
