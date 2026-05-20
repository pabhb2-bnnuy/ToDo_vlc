package com.todo.vlc.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import com.todo.vlc.Repository.ProyectoRepository;
import com.todo.vlc.model.Proyecto;
import com.todo.vlc.model.Usuario;

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

     @GetMapping("/admin")
    public String admin() {

       
        return "admin";
    }

    public void listarProyectos(Model model, Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        List<Proyecto> proyectos = proyectoRepository.findByUsuario(usuario);

        model.addAttribute("proyectos", proyectos);
    }

    @GetMapping("/menu")
    public String menu(Model model, Authentication authentication) {

        listarProyectos(model, authentication);

        return "menu";
    }

    @GetMapping("/datosProyecto")
    public String datosProyecto(Model model, Authentication authentication) {
        listarProyectos(model, authentication);
        return "datosProyecto";
    }

    @GetMapping("/")
    public String index(Authentication authentication) {

        if (AuthUtils.estaLogeado(authentication)) {
            return "redirect:/menu";
        }

        return "index";
    }

    @GetMapping("/crearTarea/{id}")
    public String crearTarea(@PathVariable Integer id, Model model) {

        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        model.addAttribute("proyecto", proyecto);

        return "crearTarea";
    }

}