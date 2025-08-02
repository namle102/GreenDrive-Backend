package com.greendrive.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Explicitly allow your frontend domain
        configuration.setAllowedOrigins(Arrays.asList(
            "https://goldfish-app-4nvzt.ondigitalocean.app",
            "https://frontend-4413-groupa-nfn6j.ondigitalocean.app",
            "https://4413groupa.netlify.app",
            "https://4413groupa.me",
            "http://localhost:3000", // For local development
            "http://localhost:3001"  // Alternative local port
        ));
        
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        
        // Add exposed headers if needed
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Register CORS for both root and API context path
        source.registerCorsConfiguration("/**", configuration);
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }


}