package com.project.crud_spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")                           // aplica a todos os endpoints
                .allowedOrigins("http://localhost:4200")     // libera o Angular na porta 4200
                .allowedMethods("*")                         // GET, POST, PUT, DELETE, etc.
                .allowedHeaders("*")                         // todos os headers
                .allowCredentials(true)                      // permite envio de cookies/autenticação
                .maxAge(3600);                               // cache de pré-voo de 1h
    }
}
