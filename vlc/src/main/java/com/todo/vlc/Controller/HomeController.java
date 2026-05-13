package com.todo.vlc.Controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String inicio() {

        return "index";
    }

    @PostMapping("/saludar")
    public String ejecutarFuncionJava(
            @RequestParam(name = "nombreUsuario") String nombre,
            Model model) {

        String mensajeFinal = "Hola " + nombre;

        model.addAttribute("resultado", mensajeFinal);

        return "index";
    }
}