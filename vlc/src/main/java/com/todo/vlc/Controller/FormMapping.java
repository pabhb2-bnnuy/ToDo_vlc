package com.todo.vlc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todo.vlc.model.Usuario;

public class FormMapping {

    @PostMapping("/register")
    public String crearUsuario(
            @RequestParam String nombre,
            @RequestParam int edad) {
        System.out.println(nombre);
        return "Usuario creado: " + nombre + ", edad: " + edad;
    }
}
