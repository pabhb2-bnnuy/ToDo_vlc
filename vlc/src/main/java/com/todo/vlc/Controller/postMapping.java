package com.todo.vlc.Controller;

import com.todo.vlc.model.Usuario;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class postMapping {

    @PostMapping("/register")
    public String iniciarSesion(

            @RequestParam("mail") String email,
            @RequestParam("password") String password

    ) {
        Usuario usuario = new Usuario(email, password);
        
        

        return "index";
    }
}