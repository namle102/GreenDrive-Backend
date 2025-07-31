package com.greendrive.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")               // apply to any /api/** endpoint
                .allowedOrigins("http://localhost:5173", "http://localhost", "http://localhost:80", "http://localhost:443") // allow your Vite dev server
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)              // if you need cookies/auth headers
                .maxAge(3600);                       // cache preflight for 1 hour
    }
}