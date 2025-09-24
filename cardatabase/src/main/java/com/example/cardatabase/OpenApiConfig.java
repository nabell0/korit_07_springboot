package com.example.cardatabase;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI carDatabaseOpenApi(){
        return new OpenAPI().info(new Info()
                .title("Car RESST API")
                .description("My car Stock")
                .version("1.0")
        );
    }
}
