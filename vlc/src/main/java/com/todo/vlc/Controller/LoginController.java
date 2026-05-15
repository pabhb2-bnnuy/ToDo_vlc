package com.todo.vlc.Controller;

import com.todo.vlc.Repository.UsuarioRepository;
import com.todo.vlc.model.Usuario;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.PasswordAuthentication;
import java.sql.Connection;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/inicioSesion")
    public String iniciarSesion(

            @RequestParam("mail") String email,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes

    ) {

        try {

            Optional<Usuario> optUsuario = usuarioRepository.findByEmail(email);

            if (optUsuario == null) {
                redirectAttributes.addFlashAttribute(
                        "error",
                        "El email no existe");
                return "redirect:/inicioSesion";
            }

            Usuario usuario = optUsuario.get();

            System.out.println(usuario.getEmail());
            System.out.println(usuario.getPasswrd());

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if (!encoder.matches(password, usuario.getPasswrd())) {

                redirectAttributes.addFlashAttribute(
                        "error",
                        "Email o contraseña incorrectos");

                return "redirect:/inicioSesion";
            }

            System.out.println("INPUT: " + password);
            System.out.println("HASH BD: " + usuario.getPasswrd());
            System.out.println("MATCH: " + encoder.matches(password, usuario.getPasswrd()));

        } catch (Exception e) {
            e.printStackTrace();

           // redirectAttributes.addFlashAttribute(
             //       "error",
               //     "Error interno del servidor");

            return "redirect:/inicioSesion";
        }

        return "inicioSesion";
    }

}