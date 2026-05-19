package com.todo.vlc.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todo.vlc.Repository.ProyectoRepository;
import com.todo.vlc.model.Proyecto;
import com.todo.vlc.model.Usuario;

@Controller
public class proyectoController {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @PostMapping("/crearProyecto")
    public String iniciarSesion(

            @AuthenticationPrincipal Usuario usuario,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("fecha") String fecha) {

        Proyecto proyecto = new Proyecto();

        proyecto.setNombre(nombre);
        proyecto.setDescripcion(descripcion);
        proyecto.setFecha_limite(fecha);
        proyecto.setEstado(""); // hay que hacer lo mismo que en el rol de usuario
        proyecto.setUsuario(usuario);

        proyectoRepository.save(proyecto);

        return "redirect:/menu";
    }

    @GetMapping("/proyecto/{idproyecto}")
    public String verProyecto(
            @PathVariable int idproyecto,
            Model model) {

        Proyecto proyecto = proyectoRepository
                .findByIdproyecto(idproyecto)
                .orElse(null);

        model.addAttribute("proyecto", proyecto);

        return "proyecto";
    }

}