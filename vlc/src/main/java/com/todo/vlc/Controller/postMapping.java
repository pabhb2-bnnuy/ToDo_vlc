package com.todo.vlc.Controller;

import com.todo.vlc.model.Usuario;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;

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
            @RequestParam("password") String password

    ) {
        Usuario usuario = new Usuario(email, password);

        try(Connection con = dataSource.getConnection();){
            
            String sql = "SELECT * FROM usuarios WHERE email = ? AND passwrd = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            String emaill = null;
            String contraseña = null;

            System.out.println(email);
            System.out.println(password);

            if (rs.next()) {
                emaill = rs.getString("email");
                contraseña = rs.getString("passwrd");
            }
            System.out.println(emaill);
            System.out.println(contraseña);

            if (emaill.equals(email)){
                if(contraseña.equals(password)){
                    return "index";
                }else{

                }
            }else{

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "inicioSesion";
    }

}