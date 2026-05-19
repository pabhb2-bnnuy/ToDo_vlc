package com.todo.vlc.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.todo.vlc.Repository.ProyectoRepository;
import com.todo.vlc.model.Proyecto;


@Controller
public class HomeController {

    @Autowired
    private ProyectoRepository proyectoRepository;


    @GetMapping("/registrarse")
    public String registro(Authentication authentication) {

        if (AuthUtils.estaLogeado(authentication)) {
            return "redirect:/menu";
        }
        return "registrarse";
    }

    @GetMapping("/menu")
    public String menu(Model model) {
        List<Proyecto> proyectos = proyectoRepository.findAll();

        model.addAttribute("proyectos", proyectos);
        return "menu";
    }

    @GetMapping("/datosProyecto")
    public String datosProyecto() {
        return "datosProyecto";
    }

    @GetMapping("/")
    public String index(Authentication authentication) {

        if (AuthUtils.estaLogeado(authentication)) {
            return "redirect:/menu";
        }

        return "index";
    }
}