package com.todo.vlc.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.todo.vlc.Repository.ProyectoRepository;
import com.todo.vlc.Repository.TareaRepository;
import com.todo.vlc.Repository.UsuarioProyectoRepository;
import com.todo.vlc.Repository.UsuarioRepository;

import com.todo.vlc.model.Proyecto;
import com.todo.vlc.model.Tarea;
import com.todo.vlc.model.Usuario;
import com.todo.vlc.model.UsuarioProyecto;

@Controller
public class TareaController {

        @Autowired
        private TareaRepository tareaRepository;

        @Autowired
        private ProyectoRepository proyectoRepository;

        @Autowired
        private UsuarioProyectoRepository usuarioProyectoRepository;

        @Autowired
        private UsuarioRepository usuarioRepository;

        // Mostrar información de la tarea para su creacion
        @GetMapping("/crearTarea/{id}")
        public String mostrarFormulario(
                        @PathVariable Integer id,
                        Model model) {

                Proyecto proyecto = proyectoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

                List<UsuarioProyecto> miembros = usuarioProyectoRepository.findByProyecto(proyecto);

                model.addAttribute("miembros", miembros);
                model.addAttribute("proyecto", proyecto);
                model.addAttribute("tarea", new Tarea());

                return "crearTarea";
        }

        // Crear una tarea
        @PostMapping("/crearTarea/{id}")
        public String guardarTarea(

                        @PathVariable Integer id,
                        @ModelAttribute Tarea tarea,
                        @RequestParam(value = "idusuario", required = false) Integer idusuario) {

                Proyecto proyecto = proyectoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

                tarea.setProyecto(proyecto);

                // asignar usuario si viene
                if (idusuario != null) {
                        Usuario usuarioAsignado = usuarioRepository.findById(idusuario)
                                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                        tarea.setUsuario(usuarioAsignado);
                }

                // estado por defecto
                if (tarea.getEstado() == null || tarea.getEstado().isEmpty()) {
                        tarea.setEstado("TODO");
                }

                tareaRepository.save(tarea);

                return "redirect:/proyecto/" + id;
        }

        // Mapeado para cambiar estado de una tarea
        @GetMapping("/tarea/estado/{id}/{estado}")
        public String cambiarEstado(
                        @PathVariable int id,
                        @PathVariable String estado,
                        Authentication auth) {

                Tarea tarea = tareaRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

                tarea.setEstado(estado);
                tareaRepository.save(tarea);

                int proyectoId = tarea.getProyecto().getIdproyecto();

                boolean isCollaborator = auth.getAuthorities().stream()
                                .anyMatch(a -> a.getAuthority().equals("ROLE_COLLABORATOR"));

                if (isCollaborator) {
                        return "redirect:/proyectocol/" + proyectoId;
                }

                return "redirect:/proyecto/" + proyectoId;
        }

        // Listar tareas en base a un proyecto
        @GetMapping("/eliminarTarea/{id}")
        public String mostrarEliminarTarea(
                        @PathVariable Integer id,
                        Model model) {

                Proyecto proyecto = proyectoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

                List<Tarea> tareas = tareaRepository.findByProyecto(proyecto);

                model.addAttribute("tareas", tareas);
                model.addAttribute("proyecto", proyecto);

                return "eliminarTarea";
        }

        // Eliminar tareas
        @PostMapping("/eliminarTarea/{id}")
        public String eliminarTarea(
                        @PathVariable Integer id,
                        @RequestParam Integer idtarea) {

                Tarea tarea = tareaRepository.findById(idtarea)
                                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

                tareaRepository.delete(tarea);

                return "redirect:/proyecto/" + id;
        }
}