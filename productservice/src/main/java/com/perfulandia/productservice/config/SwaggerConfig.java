package com.perfulandia.productservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI productoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Producto Service API")
                        .version("1.0")
                        .description("Documentación Swagger para el microservicio Producto"));
    }
}
