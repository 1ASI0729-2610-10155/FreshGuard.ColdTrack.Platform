package com.freshguard.coldtrack.platform.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Configures the public OpenAPI contract for ColdTrack. */
@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI coldTrackOpenApi() {
        var schemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info().title("FreshGuard ColdTrack Platform API").version("0.1.0").description("RESTful API for cold-chain shipment monitoring."))
                .addSecurityItem(new SecurityRequirement().addList(schemeName))
                .components(new Components().addSecuritySchemes(schemeName,
                        new SecurityScheme().name(schemeName).type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
}
