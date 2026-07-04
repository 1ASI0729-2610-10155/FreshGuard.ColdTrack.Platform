package com.freshguard.coldtrack.platform.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/** Configures the public OpenAPI contract for ColdTrack. */
@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI coldTrackOpenApi(@Value("${app.openapi.servers}") String servers) {
        var schemeName = "bearerAuth";
        var openApi = new OpenAPI()
                .info(new Info().title("FreshGuard ColdTrack Platform API").version("1.0.0").description("RESTful API for cold-chain shipment monitoring."))
                .addSecurityItem(new SecurityRequirement().addList(schemeName))
                .components(new Components().addSecuritySchemes(schemeName,
                        new SecurityScheme().name(schemeName).type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));

        Arrays.stream(servers.split(","))
                .map(String::trim)
                .filter(server -> !server.isBlank())
                .map(server -> new Server().url(server))
                .forEach(openApi::addServersItem);

        return openApi;
    }
}
