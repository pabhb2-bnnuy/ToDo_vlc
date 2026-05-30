package com.todo.vlc.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.todo.vlc.Repository.UsuarioRepository;
import com.todo.vlc.model.Rol;
import com.todo.vlc.model.Usuario;

@Controller
public class RegisterController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // User registration
    @PostMapping("/register")
    public String registrarUsuario(
            @RequestParam("nombre") String nombre,
            @RequestParam("mail") String email,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttrs) {

        // 1. Check if a user with that email already exists
        if (usuarioRepository.existsByEmail(email)) {
            redirectAttrs.addFlashAttribute("error", "Ya existe una cuenta registrada con este email.");
            return "redirect:/registrarse";
        }

        // 2. Create the new user
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRol(Rol.COLLABORATOR);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime ahora = LocalDateTime.now();
        String fechaFormateada = ahora.format(formatter);
        usuario.setFechacreacion(fechaFormateada);

        usuarioRepository.save(usuario);

        // 3. Success (e.g. success message or redirect to login)
        redirectAttrs.addFlashAttribute("success", "Usuario creado con éxito. Inicia sesión.");
        return "redirect:/inicioSesion";
    }
}