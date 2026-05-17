package com.todo.vlc.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/inicioSesion")
    public String iniciarSesion(
            @RequestParam("mail") String email,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes) {

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            SecurityContextHolder.getContext().setAuthentication(auth);
            
            redirectAttributes.addFlashAttribute(
                    "success",
                    "Login correcto");

            return "redirect:/home";

        } catch (BadCredentialsException e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Email o contraseña incorrectos");

            return "redirect:/inicioSesion";

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Error interno del servidor");

            return "redirect:/inicioSesion";
        }
    }
}