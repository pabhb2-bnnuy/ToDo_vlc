package com.todo.vlc.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.todo.vlc.Repository.ProyectoRepository;
import com.todo.vlc.Repository.TareaRepository;
import com.todo.vlc.model.Proyecto;
import com.todo.vlc.model.Tarea;

@Controller
public class TareaController {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    // ===================== MOSTRAR FORMULARIO =====================
    @GetMapping("/crearTarea/{id}")
    public String mostrarFormulario(@PathVariable Integer id, Model model) {

        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("tarea", new Tarea());

        return "crearTarea";
    }

    // ===================== GUARDAR TAREA =====================
    @PostMapping("/crearTarea/{id}")
    public String guardarTarea(@PathVariable Integer id,
                               @ModelAttribute Tarea tarea) {

        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        tarea.setProyecto(proyecto);

        // 🔥 estado por defecto SIEMPRE
        tarea.setEstado("Por hacer");

        tareaRepository.save(tarea);

        return "redirect:/proyecto/" + id;
    }
}