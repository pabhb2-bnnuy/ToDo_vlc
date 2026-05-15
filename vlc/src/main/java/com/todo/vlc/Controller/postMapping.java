package com.todo.vlc.Controller;

import com.todo.vlc.Repository.UsuarioRepository;
import com.todo.vlc.model.Usuario;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class postMapping {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/inicioSesion")
    public String iniciarSesion(

            @RequestParam("mail") String email,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes

    ) {

        try (Connection con = dataSource.getConnection()) {

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

            if (!usuario.getPasswrd().equals(password)) {
                redirectAttributes.addFlashAttribute(
                        "error",
                        "Email o contraseña incorrectos");
                return "redirect:/inicioSesion";
            }

        } catch (Exception e) {
            e.printStackTrace();

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Error interno del servidor");

            return "redirect:/inicioSesion";
        }

        return "inicioSesion";
    }

}