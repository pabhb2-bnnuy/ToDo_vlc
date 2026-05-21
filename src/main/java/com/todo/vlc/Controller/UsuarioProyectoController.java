package com.todo.vlc.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.todo.vlc.Repository.ProyectoRepository;
import com.todo.vlc.Repository.UsuarioProyectoRepository;
import com.todo.vlc.Repository.UsuarioRepository;

import com.todo.vlc.model.Proyecto;
import com.todo.vlc.model.Usuario;
import com.todo.vlc.model.UsuarioProyecto;
import com.todo.vlc.model.UsuarioProyectoId;

@Controller
public class UsuarioProyectoController {

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Autowired
        private ProyectoRepository proyectoRepository;

        @Autowired
        private UsuarioProyectoRepository usuarioProyectoRepository;

        // ===================== MOSTRAR FORM INVITAR =====================
        @GetMapping("/invitarUsuario/{id}")
        public String mostrarFormularioInvitar(
                        @PathVariable Integer id,
                        Model model) {

                Proyecto proyecto = proyectoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

                List<Usuario> usuarios = usuarioRepository.findByEnabledTrue();

                model.addAttribute("proyecto", proyecto);
                model.addAttribute("usuarios", usuarios);

                return "invitarUsuario";
        }

        // ===================== INVITAR =====================
        @PostMapping("/invitarUsuario/{id}")
        public String invitarUsuario(
                        @PathVariable Integer id,
                        @RequestParam Integer idusuario) {

                Proyecto proyecto = proyectoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

                Usuario usuario = usuarioRepository.findById(idusuario)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                boolean existe = usuarioProyectoRepository
                                .existsByUsuarioAndProyecto(usuario, proyecto);

                if (!existe) {
                        UsuarioProyecto usuarioProyecto = new UsuarioProyecto();

                        UsuarioProyectoId usuarioProyectoId = new UsuarioProyectoId(
                                        usuario.getIdusuario(),
                                        proyecto.getIdproyecto());

                        usuarioProyecto.setId(usuarioProyectoId);
                        usuarioProyecto.setUsuario(usuario);
                        usuarioProyecto.setProyecto(proyecto);

                        usuarioProyectoRepository.save(usuarioProyecto);
                }

                return "redirect:/proyecto/" + id;
        }

        // ===================== MOSTRAR FORM QUITAR =====================
        @GetMapping("/quitarUsuario/{id}")
        public String mostrarFormularioQuitar(
                        @PathVariable Integer id,
                        Model model) {

                Proyecto proyecto = proyectoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

                List<UsuarioProyecto> miembros = usuarioProyectoRepository.findByProyecto(proyecto);

                List<UsuarioProyecto> miembrosSinDueno = miembros.stream()
                                .filter(m -> !m.getUsuario().getIdusuario()
                                                .equals(proyecto.getUsuario().getIdusuario()))
                                .toList();

                model.addAttribute("proyecto", proyecto);
                model.addAttribute("miembros", miembrosSinDueno);

                return "quitarUsuario";
        }

        // ===================== QUITAR USUARIO DEL PROYECTO =====================
        @PostMapping("/quitarUsuario/{id}")
        public String quitarUsuario(
                        @PathVariable Integer id,
                        @RequestParam Integer idusuario) {

                Proyecto proyecto = proyectoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

                Usuario usuario = usuarioRepository.findById(idusuario)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                UsuarioProyecto relacion = usuarioProyectoRepository
                                .findByProyectoAndUsuario(proyecto, usuario)
                                .orElseThrow(() -> new RuntimeException("Relación no encontrada"));

                usuarioProyectoRepository.delete(relacion);

                return "redirect:/proyecto/" + id;
        }
}