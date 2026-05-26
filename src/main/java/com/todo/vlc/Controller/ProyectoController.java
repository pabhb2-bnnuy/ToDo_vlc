package com.todo.vlc.Controller;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.todo.vlc.Repository.ProyectoRepository;
import com.todo.vlc.Repository.TareaRepository;
import com.todo.vlc.Repository.UsuarioProyectoRepository;

import com.todo.vlc.model.Proyecto;
import com.todo.vlc.model.Tarea;
import com.todo.vlc.model.Usuario;
import com.todo.vlc.model.UsuarioProyecto;
import com.todo.vlc.model.UsuarioProyectoId;

@Controller
public class ProyectoController {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private UsuarioProyectoRepository usuarioProyectoRepository;

    @Autowired
    private TareaRepository tareaRepository;

    // ================= CREAR PROYECTO =================
    @PostMapping("/crearProyecto")
    public String crearProyecto(

            @AuthenticationPrincipal Usuario usuario,

            @RequestParam("nombre") String nombre,

            @RequestParam("descripcion") String descripcion,

            @RequestParam("fecha") String fecha) {

        Proyecto proyecto = new Proyecto();

        proyecto.setNombre(nombre);
        proyecto.setDescripcion(descripcion);

        proyecto.setFechainicio(String.valueOf(LocalDate.now()));

        proyecto.setFechalimite(String.valueOf(LocalDate.parse(fecha)));

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

    // ================= VER PROYECTO =================
    @GetMapping("/proyectocol/{idproyecto}")
    public String verProyectocolcol(
            @PathVariable int idproyecto,
            Model model) {

        Proyecto proyecto = proyectoRepository
                .findById(idproyecto)
                .orElse(null);

        List<UsuarioProyecto> miembros = usuarioProyectoRepository.findByProyecto(proyecto);

        // 🔥 ESTADOS CORRECTOS
        List<Tarea> tareasPorHacer = tareaRepository.findByProyectoAndEstado(proyecto, "TODO");
        List<Tarea> tareasEnProgreso = tareaRepository.findByProyectoAndEstado(proyecto, "DOING");
        List<Tarea> tareasCompletadas = tareaRepository.findByProyectoAndEstado(proyecto, "DONE");

        // Orden por prioridad: 1 alta, 2 media, 3 baja
        tareasPorHacer.sort(Comparator.comparingInt(Tarea::getPrioridad));
        tareasEnProgreso.sort(Comparator.comparingInt(Tarea::getPrioridad));
        tareasCompletadas.sort(Comparator.comparingInt(Tarea::getPrioridad));

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("miembros", miembros);
        model.addAttribute("tareasPorHacer", tareasPorHacer);
        model.addAttribute("tareasEnProgreso", tareasEnProgreso);
        model.addAttribute("tareasCompletadas", tareasCompletadas);

        return "collaborator/proyectocol";
    }

    @GetMapping("/proyecto/{idproyecto}")
public String verProyecto(
        @PathVariable int idproyecto,
        Model model) {

    Proyecto proyecto = proyectoRepository
            .findById(idproyecto)
            .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

    List<UsuarioProyecto> miembros = usuarioProyectoRepository.findByProyecto(proyecto);

    List<Tarea> tareasPorHacer = tareaRepository.findByProyectoAndEstado(proyecto, "TODO");
    List<Tarea> tareasEnProgreso = tareaRepository.findByProyectoAndEstado(proyecto, "DOING");
    List<Tarea> tareasCompletadas = tareaRepository.findByProyectoAndEstado(proyecto, "DONE");

    tareasPorHacer.sort(Comparator.comparingInt(Tarea::getPrioridad));
    tareasEnProgreso.sort(Comparator.comparingInt(Tarea::getPrioridad));
    tareasCompletadas.sort(Comparator.comparingInt(Tarea::getPrioridad));

    model.addAttribute("proyecto", proyecto);
    model.addAttribute("miembros", miembros);
    model.addAttribute("tareasPorHacer", tareasPorHacer);
    model.addAttribute("tareasEnProgreso", tareasEnProgreso);
    model.addAttribute("tareasCompletadas", tareasCompletadas);

    return "proyecto";
}

  
}