package com.todo.vlc.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                return http
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth

                                                .requestMatchers("/", "/inicioSesion", "/registrarse", "/menu").permitAll()

                                                .requestMatchers("/css/*", "/media/**").permitAll()

                                                .requestMatchers("/admin/**").hasRole("ADMIN")

                                                .anyRequest().authenticated())
                                // logout
                                .logout(logout -> logout
                                                .logoutUrl("/cerrarSesion")
                                                .logoutSuccessUrl("/inicioSesion")
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID"))
                                .build();

        }

}