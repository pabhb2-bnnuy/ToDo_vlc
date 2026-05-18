package com.todo.vlc.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration config)
                        throws Exception {

                return config.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http)
                        throws Exception {

                return http

                                .csrf(csrf -> csrf.disable())

                                .authorizeHttpRequests(auth -> auth

                                                .requestMatchers(
                                                                "/",
                                                                "/inicioSesion",
                                                                "/registrarse",
                                                                "/register",
                                                                "/css/**",
                                                                "/media/**",
                                                                "/menu",
                                                                "/datosProyecto")
                                                .permitAll()

                                                .requestMatchers("/admin/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers("/test")
                                                .hasRole("COLLABORATOR")

                                                .anyRequest()
                                                .authenticated())

                                .formLogin(form -> form

                                                // GET -> mostrar página login
                                                .loginPage("/inicioSesion")

                                                // POST -> procesado automáticamente
                                                .loginProcessingUrl("/inicioSesion")

                                                // nombres de inputs
                                                .usernameParameter("mail")
                                                .passwordParameter("password")

                                                // login OK
                                                .defaultSuccessUrl("/?loginSuccess", true)

                                                // login ERROR
                                                .failureUrl("/inicioSesion?error")

                                                .permitAll())

                                .logout(logout -> logout

                                                .logoutUrl("/cerrarSesion")

                                                .logoutSuccessUrl("/inicioSesion")

                                                .invalidateHttpSession(true)

                                                .deleteCookies("JSESSIONID"))

                                .build();
        }
}