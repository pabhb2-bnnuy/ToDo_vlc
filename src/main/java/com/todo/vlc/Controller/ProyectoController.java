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

        // Create project
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

        // List project for collaborator
        @GetMapping("/proyectocol/{idproyecto}")
        public String verProyectocolcol(
                        @PathVariable int idproyecto,
                        Model model) {

                Proyecto proyecto = proyectoRepository
                                .findById(idproyecto)
                                .orElse(null);

                List<UsuarioProyecto> miembros = usuarioProyectoRepository.findByProyecto(proyecto);

                List<Tarea> tareasPorHacer = tareaRepository.findByProyectoAndEstado(proyecto, "TODO");
                List<Tarea> tareasEnProgreso = tareaRepository.findByProyectoAndEstado(proyecto, "DOING");
                List<Tarea> tareasCompletadas = tareaRepository.findByProyectoAndEstado(proyecto, "DONE");

                // Order by priority: 1 high, 2 medium, 3 low
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

        // List project for admin and manager
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

        // Show project where you are located
        @GetMapping("/proyecto/cambiarEstado/{id}")
        public String mostrarCambioEstado(
                        @PathVariable int id,
                        Model model) {

                Proyecto proyecto = proyectoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

                model.addAttribute("proyecto", proyecto);

                return "cambiarEstadoProyecto";
        }

        // Change project status
        @PostMapping("/proyecto/cambiarEstado/{id}")
        public String cambiarEstadoProyecto(
                        @PathVariable int id,
                        @RequestParam("estado") String estado) {

                Proyecto proyecto = proyectoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

                proyecto.setEstado(estado);

                proyectoRepository.save(proyecto);

                return "redirect:/proyecto/" + id;
        }

        // List projects to be able to delete them
        @GetMapping("/eliminarProyecto")
        public String vistaEliminarProyecto(
                        @AuthenticationPrincipal Usuario usuario,
                        Model model) {

                List<Proyecto> proyectos = proyectoRepository.findByUsuario(usuario);

                model.addAttribute("proyectos", proyectos);

                return "eliminarProyecto";
        }

        // Delete projects
        @PostMapping("/eliminarProyecto")
        public String eliminarProyecto(
                        @RequestParam("idproyecto") int idproyecto) {

                Proyecto proyecto = proyectoRepository.findById(idproyecto)
                                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

                proyectoRepository.delete(proyecto);

                return "redirect:/menu";
        }

}