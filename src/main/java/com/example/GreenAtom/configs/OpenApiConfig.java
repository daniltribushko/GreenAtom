package com.example.GreenAtom.configs;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@SecurityScheme(
        name = "jwtAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("GreenAtom internship")
                .description("Test task for selection for an internship at GreenAtom")).tags(List.of(new Tag()
                        .name("Client Api")
                        .description("Api for client"),
                new Tag()
                        .name("Admin Api")
                        .description("Api for admins"),
                new Tag()
                        .name("Auth Controller")
                        .description("Controller for user authorization and registration")));
    }

}
