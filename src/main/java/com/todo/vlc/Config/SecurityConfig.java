package com.todo.vlc.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
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
                                                // URLS sin iniciar sesión.
                                                .requestMatchers(
                                                                "/",
                                                                "/inicioSesion",
                                                                "/registrarse",
                                                                "/register",
                                                                "/css/**",
                                                                "/media/**",
                                                                "/datosProyecto")
                                                .permitAll()

                                                // URLS para admin
                                                .requestMatchers("/admin/**")
                                                .hasRole("ADMIN")

                                                // Urls para Colaborador
                                                .requestMatchers("/menu")
                                                .hasRole("COLLABORATOR")
                                                // URLS para Gestor
                                                .requestMatchers("/menu/gestion")
                                                .hasRole("GESTOR")

                                                .anyRequest()
                                                .authenticated())

                                .formLogin(form -> form

                                                // Página login (GET)
                                                .loginPage("/inicioSesion")

                                                // Procesar login (POST)
                                                .loginProcessingUrl("/inicioSesion")

                                                // Names del formulario
                                                .usernameParameter("mail")
                                                .passwordParameter("password")

                                                // Login correcto
                                                .defaultSuccessUrl("/?loginSuccess", true)

                                                // Login incorrecto personalizado
                                                .failureHandler((request, response, exception) -> {

                                                        if (exception instanceof DisabledException) {

                                                                response.sendRedirect(
                                                                                "/inicioSesion?disabled");

                                                        } else {

                                                                response.sendRedirect(
                                                                                "/inicioSesion?error");
                                                        }
                                                })

                                                .permitAll())

                                // PERSISTENCIA DE SESIÓN
                                .rememberMe(remember -> remember

                                                // name="" del checkbox
                                                .rememberMeParameter("remember-me")

                                                // clave interna
                                                .key("vlc-clave-secreta-2026"))

                                // Cerrar sesión.
                                .logout(logout -> logout

                                                .logoutUrl("/cerrarSesion")

                                                .logoutSuccessUrl("/inicioSesion")

                                                .invalidateHttpSession(true)

                                                .deleteCookies(
                                                                "JSESSIONID",
                                                                "remember-me"))

                                .build();
        }
}