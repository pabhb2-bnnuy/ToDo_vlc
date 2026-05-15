package com.todo.vlc.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // rutas públicas
                        .requestMatchers("/", "/home", "/registro").permitAll()

                        // rutas privadas
                        .requestMatchers("/admin/**").authenticated()

                        // todo lo demás público
                        .anyRequest().permitAll())

                // activar formulario login
                .formLogin(form -> form.permitAll());

        return http.build();
    }
}