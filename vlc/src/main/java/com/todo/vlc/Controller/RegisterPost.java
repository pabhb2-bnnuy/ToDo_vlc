package com.todo.vlc.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterPost {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;

    @PostMapping("/register")
    public String iniciarSesion(

            @RequestParam("nombre") String nombre,
            @RequestParam("mail") String email,
            @RequestParam("password") String password,
            @RequestParam("rol") String rol) {

        try (Connection con = dataSource.getConnection()) {

             password = passwordEncoder.encode(password);

            String sql = "INSERT INTO usuarios(nombre, email, passwrd, rol) VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nombre);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, rol);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                return "index";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "registrarse";
    }

}