package com.todo.vlc.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/inicioSesion")
    public String login(
            Authentication authentication,
            @RequestParam(value = "error", required = false) String error,
            Model model) {

        if (AuthUtils.estaLogeado(authentication)) {
            return "redirect:/menu";
        }

        if (error != null) {

            model.addAttribute(
                    "error",
                    "Email o contraseña incorrectos");
        }

        return "inicioSesion";
    }


    @GetMapping("/")
    public String index(Authentication authentication) {

        if (AuthUtils.estaLogeado(authentication)) {
            return "redirect:/menu";
        }

        return "index";
    }

}