package com.bromatologia.backend.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // a todas las rutas
                .allowedOrigins("https://localhost:4200") // al front de angular
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders()
                        .allowCredentials(true);
            }
        };
    }
}
