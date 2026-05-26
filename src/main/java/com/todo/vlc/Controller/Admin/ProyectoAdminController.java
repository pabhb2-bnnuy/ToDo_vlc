package com.todo.vlc.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.todo.vlc.Repository.ProyectoRepository;
import com.todo.vlc.Repository.TareaRepository;
import com.todo.vlc.Repository.UsuarioProyectoRepository;
import com.todo.vlc.Repository.UsuarioRepository;

import com.todo.vlc.model.*;

@Controller
@RequestMapping("/admin")
public class ProyectoAdminController {

    @Autowired
    private UsuarioProyectoRepository usuarioProyectoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    // =====================================================
    // ================= LISTA PROYECTOS ===================
    // =====================================================

    @GetMapping("/proyectos")
    public String proyectos(Model model) {

        model.addAttribute(
                "proyectos",
                proyectoRepository.findAll());

        return "admin/proyectos";
    }

    // =====================================================
    // ================= EDITAR PROYECTO ===================
    // =====================================================
    @Autowired
    private TareaRepository tareaRepository;

    @GetMapping("/proyecto/{id}")
    public String editarProyecto(

            @PathVariable Integer id,

            Model model) {

        Proyecto proyecto = proyectoRepository
                .findById(id)
                .orElseThrow();

        model.addAttribute(
                "proyecto",
                proyecto);

        model.addAttribute(
                "tareas",
                tareaRepository.findByProyecto(proyecto));

        return "admin/editarProyecto";
    }

    // =====================================================
    // ============== GUARDAR PROYECTO =====================
    // =====================================================

    @PostMapping("/editarProyecto/{id}")
    public String guardarProyecto(

            @PathVariable Integer id,

            @RequestParam String nombre,

            @RequestParam String descripcion,

            @RequestParam String estado,

            @RequestParam String fecha_limite) {

        Proyecto proyecto = proyectoRepository
                .findById(id)
                .orElseThrow();

        proyecto.setNombre(nombre);

        proyecto.setDescripcion(descripcion);

        proyecto.setEstado(estado);

        proyecto.setFechalimite(fecha_limite);

        proyectoRepository.save(proyecto);

        return "redirect:/admin/proyectos";
    }

    // =====================================================
    // ================= ELIMINAR ==========================
    // =====================================================

    @PostMapping("/eliminarTarea/{id}")
    public String eliminarTarea(
            @PathVariable Integer id) {

        Tarea tarea = tareaRepository
                .findById(id)
                .orElseThrow();

        Integer idProyecto = tarea.getProyecto().getIdproyecto();

        tareaRepository.delete(tarea);

        return "redirect:/admin/proyecto/" + idProyecto;
    }

    @PostMapping("/eliminarProyecto/{id}")
    public String eliminarProyecto(
            @PathVariable Integer id) {

        proyectoRepository.deleteById(id);

        return "redirect:/admin/proyectos";
    }

    // =====================================================
    // ================= ASIGNAR ===========================
    // =====================================================

    @PostMapping("/asignarProyecto")
    public String asignarProyecto(

            @RequestParam Integer idusuario,

            @RequestParam Integer idproyecto) {

        Usuario usuario = usuarioRepository
                .findById(idusuario)
                .orElseThrow();

        Proyecto proyecto = proyectoRepository
                .findById(idproyecto)
                .orElseThrow();

        boolean existe = usuarioProyectoRepository
                .existsByUsuarioAndProyecto(
                        usuario,
                        proyecto);

        if (!existe) {

            UsuarioProyecto usuarioProyecto = new UsuarioProyecto();

            UsuarioProyectoId id = new UsuarioProyectoId(
                    usuario.getIdusuario(),
                    proyecto.getIdproyecto());

            usuarioProyecto.setId(id);

            usuarioProyecto.setUsuario(usuario);

            usuarioProyecto.setProyecto(proyecto);

            usuarioProyectoRepository.save(usuarioProyecto);
        }

        return "redirect:/admin/usuario/" + idusuario;
    }

    // =====================================================
    // ================= QUITAR ============================
    // =====================================================

    @PostMapping("/quitarProyecto")
    public String quitarProyecto(

            @RequestParam Integer idusuario,

            @RequestParam Integer idproyecto) {

        Usuario usuario = usuarioRepository
                .findById(idusuario)
                .orElseThrow();

        Proyecto proyecto = proyectoRepository
                .findById(idproyecto)
                .orElseThrow();

        usuarioProyectoRepository
                .deleteByUsuarioAndProyecto(
                        usuario,
                        proyecto);

        return "redirect:/admin/usuario/" + idusuario;

    }

    @GetMapping("/tarea/{id}")
    public String editarTarea(

            @PathVariable Integer id,

            Model model) {

        Tarea tarea = tareaRepository
                .findById(id)
                .orElseThrow();

        Proyecto proyecto = tarea.getProyecto();

        model.addAttribute(
                "tarea",
                tarea);

        model.addAttribute(
                "usuarios",
                usuarioProyectoRepository
                        .findByProyecto(proyecto));

        return "admin/editarTarea";
    }

    @PostMapping("/editarTarea/{id}")
    public String guardarTarea(

            @PathVariable Integer id,

            @RequestParam String titulo,

            @RequestParam String descripcion,

            @RequestParam Integer prioridad,

            @RequestParam String estado,

            @RequestParam String fecha_entrega,

            @RequestParam Integer idusuario) {

        Tarea tarea = tareaRepository
                .findById(id)
                .orElseThrow();

        Usuario usuario = usuarioRepository
                .findById(idusuario)
                .orElseThrow();

        tarea.setTitulo(titulo);

        tarea.setDescripcion(descripcion);

        tarea.setPrioridad(prioridad);

        tarea.setEstado(estado);

        tarea.setFechaentrega(fecha_entrega);

        tarea.setUsuario(usuario);

        tareaRepository.save(tarea);

        return "redirect:/admin/proyecto/"
                + tarea.getProyecto().getIdproyecto();
    }
}
