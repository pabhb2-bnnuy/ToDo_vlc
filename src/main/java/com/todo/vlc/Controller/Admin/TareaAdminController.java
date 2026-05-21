package com.todo.vlc.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.todo.vlc.Repository.TareaRepository;
import com.todo.vlc.Repository.UsuarioRepository;

import com.todo.vlc.model.*;

@Controller
@RequestMapping("/admin")
public class TareaAdminController {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ===================== ASIGNAR TAREA =====================

    @PostMapping("/asignarTarea")
    public String asignarTarea(
            @RequestParam Integer idusuario,
            @RequestParam Integer idtarea) {

        Usuario usuario = usuarioRepository.findById(idusuario)
                .orElseThrow();

        Tarea tarea = tareaRepository.findById(idtarea)
                .orElseThrow();

        tarea.setUsuario(usuario);

        tareaRepository.save(tarea);

        return "redirect:/admin/usuario/" + idusuario;
    }

    // ===================== QUITAR TAREA =====================

    @PostMapping("/quitarTarea/{id}")
    public String quitarTarea(
            @PathVariable Integer id) {

        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow();

        Integer idusuario =
                tarea.getUsuario().getIdusuario();

        tarea.setUsuario(null);

        tareaRepository.save(tarea);

        return "redirect:/admin/usuario/" + idusuario;
    }
}