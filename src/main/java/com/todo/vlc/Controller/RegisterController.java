package com.todo.vlc.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todo.vlc.Repository.UsuarioRepository;
import com.todo.vlc.model.Rol;
import com.todo.vlc.model.Usuario;

@Controller
public class RegisterController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/register")
    public String iniciarSesion(

            @RequestParam("nombre") String nombre,
            @RequestParam("mail") String email,
            @RequestParam("password") String password) {

        Usuario usuario = new Usuario();

        usuario.setEmail(email);
        usuario.setPasswrd(passwordEncoder.encode(password));
        usuario.setRol(Rol.ROLE_COLLABORATOR);
        usuario.setNombre(nombre);

        usuarioRepository.save(usuario);
        return "iniciarSesion";
    }

}