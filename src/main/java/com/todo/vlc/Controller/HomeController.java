package com.todo.vlc.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.todo.vlc.Repository.UsuarioProyectoRepository;
import com.todo.vlc.model.Proyecto;
import com.todo.vlc.model.Usuario;
import com.todo.vlc.model.UsuarioProyecto;

@Controller
public class HomeController {

    @Autowired
    private UsuarioProyectoRepository usuarioProyectoRepository;



    @GetMapping("/registrarse")
    public String registro(Authentication authentication) {

        if (AuthUtils.estaLogeado(authentication)) {
            return "redirect:/menu";
        }
        return "registrarse";
    }

    public void listarProyectos(
            Model model,
            Authentication authentication) {

        Usuario usuario = (Usuario) authentication.getPrincipal();

        List<UsuarioProyecto> relaciones = usuarioProyectoRepository.findByUsuario(usuario);

        List<Proyecto> proyectos = relaciones.stream()
                .map(UsuarioProyecto::getProyecto)
                .toList();

        model.addAttribute(
                "proyectos",
                proyectos);
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

}