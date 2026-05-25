package com.todo.vlc.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import com.todo.vlc.Repository.UsuarioProyectoRepository;
import com.todo.vlc.Repository.UsuarioRepository;
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

    @GetMapping("/perfil")
    public String perfil(Model model, Authentication authentication) {

        Usuario usuario = (Usuario) authentication.getPrincipal();

        model.addAttribute("usuario", usuario);

        return "perfil";
    }

    @GetMapping("/cambiar-password")
    public String cambiarPassword() {
        return "cambiarPassword";
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/cambiar-password")
    public String actualizarPassword(
            Authentication authentication,
            @RequestParam("actual") String actual,
            @RequestParam("nueva") String nueva,
            @RequestParam("repetir") String repetir,
            Model model) {

        Usuario usuario = (Usuario) authentication.getPrincipal();

        // 1. comprobar contraseña actual
        if (!passwordEncoder.matches(actual, usuario.getPasswrd())) {
            model.addAttribute("error", "La contraseña actual no es correcta");
            return "cambiarPassword";
        }

        // 2. comprobar repetición
        if (!nueva.equals(repetir)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "cambiarPassword";
        }

        // 3. guardar nueva encriptada
        usuario.setPasswrd(passwordEncoder.encode(nueva));
        usuarioRepository.save(usuario);

        model.addAttribute("success", "Contraseña actualizada correctamente");

        return "cambiarPassword";
    }

}