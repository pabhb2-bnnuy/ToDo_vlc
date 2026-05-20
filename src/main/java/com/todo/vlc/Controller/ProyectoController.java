package com.todo.vlc.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.todo.vlc.Repository.ProyectoRepository;
import com.todo.vlc.Repository.UsuarioProyectoRepository;

import com.todo.vlc.model.Proyecto;
import com.todo.vlc.model.Usuario;
import com.todo.vlc.model.UsuarioProyecto;
import com.todo.vlc.model.UsuarioProyectoId;

@Controller
public class ProyectoController {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private UsuarioProyectoRepository usuarioProyectoRepository;

    @PostMapping("/crearProyecto")
    public String crearProyecto(

            @AuthenticationPrincipal Usuario usuario,

            @RequestParam("nombre") String nombre,

            @RequestParam("descripcion") String descripcion,

            @RequestParam("fecha") String fecha) {

        // CREAR PROYECTO

        Proyecto proyecto = new Proyecto();

        proyecto.setNombre(nombre);

        proyecto.setDescripcion(descripcion);

        proyecto.setFecha_inicio(String.valueOf(LocalDate.now()));

        proyecto.setFecha_limite(String.valueOf(LocalDate.parse(fecha)));

        proyecto.setEstado("PENDIENTE");

        proyecto.setUsuario(usuario);

        proyectoRepository.save(proyecto);

        // AÑADIR CREADOR A TABLA INTERMEDIA

        UsuarioProyecto usuarioProyecto = new UsuarioProyecto();

        UsuarioProyectoId id = new UsuarioProyectoId(
                usuario.getIdusuario(),
                proyecto.getIdproyecto());

        usuarioProyecto.setId(id);

        usuarioProyecto.setUsuario(usuario);

        usuarioProyecto.setProyecto(proyecto);

        usuarioProyectoRepository.save(usuarioProyecto);

        return "redirect:/menu";
    }

    @GetMapping("/proyecto/{idproyecto}")
    public String verProyecto(
            @PathVariable int idproyecto,
            Model model) {

        Proyecto proyecto = proyectoRepository
                .findById(idproyecto)
                .orElse(null);

        List<UsuarioProyecto> miembros = usuarioProyectoRepository.findByProyecto(proyecto);

        model.addAttribute("proyecto", proyecto);

        model.addAttribute("miembros", miembros);

        return "proyecto";
    }
}