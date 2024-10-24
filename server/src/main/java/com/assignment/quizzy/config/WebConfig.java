//package com.assignment.quizzy.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/api/**") // Adjust the mapping as needed
//                .allowedOrigins("http://localhost:5173") // Your React app's origin
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed methods
//                .allowedHeaders("*") // Allows all headers, or specify if needed
//                .allowCredentials(true); // If you're sending cookies or using HTTP authentication
//    }
//}
