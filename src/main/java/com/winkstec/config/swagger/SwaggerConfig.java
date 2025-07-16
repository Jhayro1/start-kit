package com.winkstec.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Winkstec API")
                        .version("1.0.0")
                        .description("""
                    Winkstec es una plataforma SaaS diseñada especialmente para casas de cambio en Perú y Latinoamérica.
                    Esta API permite gestionar de forma segura y eficiente múltiples empresas, usuarios, roles, permisos, reportes y procesos internos de operación.
                    
                    🔐 Seguridad:
                    - Autenticación basada en JWT (JSON Web Token).
                    - Control de acceso multiempresa con separación de datos.
                    
                    📦 Funcionalidades principales:
                    - Gestión de empresas y usuarios
                    - Asignación de roles y permisos personalizados
                    - Soporte multiusuario por empresa
                    - Auditoría completa de acciones (quién, cuándo, y en qué entidad)
                    - Soporte para integración con sistemas de reportes y almacenamiento externo
                    
                    ⚠️ Nota: Solo ciertos endpoints requieren autenticación con JWT. Aparecen marcados como protegidos en esta documentación.
                    """)
                        .contact(new Contact()
                                .name("Soporte Winkstec")
                                .email("soporte@winkstec.com")
                                .url("https://winkstec.com"))
                        .license(new License()
                                .name("Licencia Propietaria - Winkstec")
                                .url("https://winkstec.com/licencia"))
                )
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Introduce tu JWT en el formato: Bearer {token}")
                        )
                );
    }
}
