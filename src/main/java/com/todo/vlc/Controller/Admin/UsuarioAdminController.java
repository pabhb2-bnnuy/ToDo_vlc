package com.todo.vlc.Controller.Admin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        // show every user

        @GetMapping("/usuarios")
        public String usuarios(Model model) {

                model.addAttribute(
                                "usuarios",
                                usuarioRepository.findAll());

                return "admin/usuarios";
        }

        // show user details

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

        // create a user

        @PostMapping("/crearUsuario")
        public String crearUsuario(

                        @RequestParam("nombre") String nombre,
                        @RequestParam("email") String email,
                        @RequestParam("password") String password,
                        @RequestParam("rol") String rol) {

                Usuario usuario = new Usuario();

                usuario.setEmail(email);
                usuario.setPassword(
                                passwordEncoder.encode(password));
                usuario.setRol(Rol.valueOf(rol.toUpperCase()));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

                LocalDateTime ahora = LocalDateTime.now();
                String fechaFormateada = ahora.format(formatter);
                usuario.setFechacreacion(fechaFormateada);
                usuario.setNombre(nombre);
                usuario.setEnabled(true);

                usuarioRepository.save(usuario);
                return "redirect:/admin/usuarios";
        }

        @GetMapping("/crearUsuario")
        public String crearusuarioo() {
                return "admin/crearUsuario";
        }

        // change user rol

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

        // enable or disabled a user

        @PostMapping("/toggleUsuario/{id}")
        public String toggleUsuario(
                        @PathVariable Integer id) {

                Usuario usuario = usuarioRepository.findById(id)
                                .orElseThrow();

                usuario.setEnabled(!usuario.isEnabled());

                usuarioRepository.save(usuario);

                return "redirect:/admin/usuario/" + id;
        }

        // load user's projects

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

        // load user's tasks

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