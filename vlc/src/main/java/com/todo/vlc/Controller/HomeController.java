package com.todo.vlc.Controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

<<<<<<< HEAD
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
=======
>>>>>>> fe4fce8 (cambios en base de datos, creando registro usuario)
    }
}