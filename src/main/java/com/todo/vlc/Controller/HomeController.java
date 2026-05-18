package com.todo.vlc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class HomeController {

    @GetMapping("/inicioSesion")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            Model model) {

        if (error != null) {

            model.addAttribute(
                    "error",
                    "Email o contraseña incorrectos");
        }

        return "inicioSesion";
    }

    @GetMapping("/registrarse")
    public String registro() {
        return "registrarse";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }
}