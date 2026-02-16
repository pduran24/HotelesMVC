package org.example.turismoapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI (Swagger) para la documentación de la API.
 * Define la información general de la API y los esquemas de seguridad.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "TurismoApp API - Pirineos Edition",
                version = "1.0",
                description = "Documentación de la API para gestión de hoteles y reservas.",
                contact = @Contact(name = "Duragui", email = "tu@email.com"),
                license = @License(name = "Apache 2.0", url = "http://springdoc.org")
        ),
        security = @SecurityRequirement(name = "basicAuth")
)
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class OpenApiConfig {
}
