package com.todo.vlc.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    // Mecanismo de redireccion por si ya estas logeado y si tienes tu cuenta
    // deshabilitada o no
    // Tambien hace el mapeo a /inicioSesion
    @GetMapping("/inicioSesion")
    public String login(
            Authentication authentication,

            @RequestParam(value = "error", required = false) String error,

            @RequestParam(value = "disabled", required = false) String disabled,

            Model model) {

        if (AuthUtils.estaLogeado(authentication)) {
            return "redirect:/menu";
        }

        if (error != null) {

            model.addAttribute(
                    "error",
                    "Email o contraseña incorrectos");
        }

        if (disabled != null) {

            model.addAttribute(
                    "error",
                    "Tu cuenta está deshabilitada");
        }

        return "inicioSesion";
    }
}