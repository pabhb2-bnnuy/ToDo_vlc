package com.todo.vlc.Controller.Admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UsuarioAdminController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioProyectoRepository usuarioProyectoRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ===================== LISTA USUARIOS =====================

    @GetMapping("/usuarios")
    public String usuarios(Model model) {

        model.addAttribute(
                "usuarios",
                usuarioRepository.findAll());

        return "admin/usuarios";
    }

    // ===================== DETALLE USUARIO =====================

    @GetMapping("/usuario/{id}")
    public String detalleUsuario(
            @PathVariable Integer id,
            Model model) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow();

        List<UsuarioProyecto> proyectos = usuarioProyectoRepository.findByUsuario(usuario);

        List<Tarea> tareas = tareaRepository.findByUsuario(usuario);

        model.addAttribute("usuario", usuario);

        model.addAttribute("proyectos", proyectos);

        model.addAttribute("tareas", tareas);

        return "admin/detalleUsuario";
    }

    // ===================== FORM CREAR USUARIO =====================

    @GetMapping("/crearUsuario")
    public String crearUsuarioForm(Model model) {

        model.addAttribute(
                "usuario",
                new Usuario());

        return "admin/crearUsuario";
    }

    // ===================== CREAR USUARIO =====================

    @PostMapping("/crearUsuario")
    public String crearUsuario(
            @ModelAttribute Usuario usuario) {

        usuario.setPasswrd(
                passwordEncoder.encode(usuario.getPassword()));

        usuario.setEnabled(true);

        usuarioRepository.save(usuario);

        return "redirect:/admin/usuarios";
    }

    // ===================== CAMBIAR ROL =====================

    @PostMapping("/cambiarRol/{id}")
    public String cambiarRol(
            @PathVariable Integer id,
            @RequestParam Rol rol) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow();

        usuario.setRol(rol);

        usuarioRepository.save(usuario);

        return "redirect:/admin/usuario/" + id;
    }

    // ===================== ACTIVAR / DESACTIVAR =====================

    @PostMapping("/toggleUsuario/{id}")
    public String toggleUsuario(
            @PathVariable Integer id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow();

        usuario.setEnabled(!usuario.isEnabled());

        usuarioRepository.save(usuario);

        return "redirect:/admin/usuario/" + id;
    }

    // ===================== FORM ASIGNAR PROYECTO =====================

    @GetMapping("/asignarProyecto/{id}")
    public String asignarProyectoForm(
            @PathVariable Integer id,
            Model model) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow();

        model.addAttribute("usuario", usuario);

        model.addAttribute(
                "proyectos",
                proyectoRepository.findAll());

        return "admin/asignarProyecto";
    }

    // ===================== FORM ASIGNAR TAREA =====================

    @GetMapping("/asignarTarea/{id}")
    public String asignarTareaForm(
            @PathVariable Integer id,
            Model model) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow();

        model.addAttribute("usuario", usuario);

        model.addAttribute(
                "tareas",
                tareaRepository.findAll());

        return "admin/asignarTarea";
    }
}