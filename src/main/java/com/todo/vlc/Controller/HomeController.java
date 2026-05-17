package com.todo.vlc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String inicio() {

        return "index";
    }

    @GetMapping("/registrarse")
    public String registro() {
        return "registrarse";
    }

    @GetMapping("/inicioSesion")
    public String inicioSesion() {
        return "inicioSesion";
    }
}