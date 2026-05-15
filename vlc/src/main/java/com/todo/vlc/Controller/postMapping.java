package com.todo.vlc.Controller;

import com.todo.vlc.model.Usuario;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class postMapping {

    @Autowired
    private DataSource dataSource;

    @PostMapping("/inicioSesion")
    public String iniciarSesion(

            @RequestParam("mail") String email,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes

    ) {
        

        try (Connection con = dataSource.getConnection()) {

            String sql = "SELECT * FROM usuarios WHERE email = ?";
                String sql2 = "SELECT * FROM usuarios WHERE passwrd = ?";


            PreparedStatement ps = con.prepareStatement(sql);
            PreparedStatement ps2 = con.prepareStatement(sql2);

            ps.setString(1,"placeholder" );
            ps2.setString(1, "placeholder");

            ResultSet rs = ps.executeQuery();
            ResultSet rs2 = ps2.executeQuery();

            String emaill = null;
            String contraseña = null;

            System.out.println(email);
            System.out.println(password);

            if (rs.next()) {
                emaill = rs.getString("email");
            }

            if (rs2.next()) {
                contraseña = rs.getString("passwrd");
            }
            System.out.println(emaill);
            System.out.println(contraseña);


            if (emaill == null) {
                redirectAttributes.addFlashAttribute(
                        "error",
                        "El email no existe"
                );
                return "redirect:/inicioSesion";
            }

            if (contraseña == null) {
                redirectAttributes.addFlashAttribute(
                        "error",
                        "Email o contraseña incorrectos"
                );
                return "redirect:/inicioSesion";
            }

    

        } catch (Exception e) {
            e.printStackTrace();

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Error interno del servidor"
            );

            return "redirect:/inicioSesion";
        }

        return "inicioSesion";
    }

}