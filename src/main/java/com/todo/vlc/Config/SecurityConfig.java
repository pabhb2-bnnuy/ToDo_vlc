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

                                                // Públicas
                                                .requestMatchers(
                                                                "/",
                                                                "/inicioSesion",
                                                                "/registrarse",
                                                                "/register",
                                                                "/media/**",
                                                                "/webjars/**",
                                                                "/cerrarSesion")
                                                .permitAll()

                                                // Admin
                                                .requestMatchers("/admin/**")
                                                .hasRole("ADMIN")

                                                // Todos los roles
                                                .requestMatchers("/tarea/**")
                                                .hasAnyRole("GESTOR", "ADMIN", "COLLABORATOR")

                                                // Collaborator
                                                .requestMatchers(
                                                                "/menucol",
                                                                "/proyectocol/**",
                                                                "/perfilcol",
                                                                "/cambiar-passwordcol")
                                                .hasRole("COLLABORATOR")

                                                // Gestor/Admin
                                                .requestMatchers(
                                                                "/datosProyecto",
                                                                "/menu",
                                                                "/proyecto/**",
                                                                "/perfil",
                                                                "/cambiar-password")
                                                .hasAnyRole("GESTOR", "ADMIN")

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

                                                .successHandler((request, response, authentication) -> {

                                                        var authorities = authentication.getAuthorities();

                                                        boolean isAdmin = authorities.stream()
                                                                        .anyMatch(a -> a.getAuthority()
                                                                                        .equals("ROLE_ADMIN"));

                                                        boolean isGestor = authorities.stream()
                                                                        .anyMatch(a -> a.getAuthority()
                                                                                        .equals("ROLE_GESTOR"));

                                                        boolean isCollaborator = authorities.stream()
                                                                        .anyMatch(a -> a.getAuthority()
                                                                                        .equals("ROLE_COLLABORATOR"));

                                                        if (isAdmin) {
                                                                response.sendRedirect("/admin");
                                                        } else if (isGestor) {
                                                                response.sendRedirect("/menu");
                                                        } else if (isCollaborator) {
                                                                response.sendRedirect("/menucol");
                                                        } else {
                                                                response.sendRedirect("/");
                                                        }
                                                })

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