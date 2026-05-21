package com.todo.vlc.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.todo.vlc.Repository.ProyectoRepository;
import com.todo.vlc.Repository.TareaRepository;
import com.todo.vlc.Repository.UsuarioRepository;

@Controller
public class AdminController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private TareaRepository tareaRepository;
    
    // ================= DASHBOARD =================

    @GetMapping("/admin")
    public String adminDashboard(Model model) {

        model.addAttribute(
                "totalUsuarios",
                usuarioRepository.count());

        model.addAttribute(
                "usuariosActivos",
                usuarioRepository.findByEnabledTrue().size());

        model.addAttribute(
                "totalProyectos",
                proyectoRepository.count());

        model.addAttribute(
                "totalTareas",
                tareaRepository.count());

        return "admin/adminDashboard";
    }
}