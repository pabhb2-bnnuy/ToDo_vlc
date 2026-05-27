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

import com.todo.vlc.Repository.ProyectoRepository;
import com.todo.vlc.Repository.UsuarioProyectoRepository;
import com.todo.vlc.Repository.UsuarioRepository;
import com.todo.vlc.model.Proyecto;
import com.todo.vlc.model.Usuario;
import com.todo.vlc.model.UsuarioProyecto;

@Controller
public class HomeController {

    @Autowired
    private UsuarioProyectoRepository usuarioProyectoRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    // Mapping para registrarse
    @GetMapping("/registrarse")
    public String registro(Authentication authentication) {

        if (AuthUtils.estaLogeado(authentication)) {
            return "redirect:/menu";
        }
        return "registrarse";
    }

    // Mapping para menu que lista todos los proyectos (este mapping se usa para
    // admin y gestor)

    @GetMapping("/menu")
    public String menu(Model model, Authentication authentication) {

        List<Proyecto> proyectos = proyectoRepository.findAll();

        model.addAttribute("proyectos", proyectos);

        return "menu";
    }

    @GetMapping("/datosProyecto")
    public String datosProyecto(Model model, Authentication authentication) {
        listarProyectos(model, authentication);
        return "datosProyecto";
    }

    // Redirect a /menu si estas logeado y si no a /
    @GetMapping("/")
    public String index(Authentication authentication) {

        if (AuthUtils.estaLogeado(authentication)) {
            return "redirect:/menu";
        }

        return "index";
    }

    // Perfil de usuario para admin y gestor
    @GetMapping("/perfil")
    public String perfil(Model model, Authentication authentication) {

        Usuario usuario = (Usuario) authentication.getPrincipal();

        model.addAttribute("usuario", usuario);

        return "perfil";
    }
    // Perfil de usuario para collaborator

    @GetMapping("/perfilcol")
    public String perfilcol(Model model, Authentication authentication) {

        Usuario usuario = (Usuario) authentication.getPrincipal();

        model.addAttribute("usuario", usuario);

        return "/collaborator/perfilcol";
    }

    @GetMapping("/cambiar-password")
    public String cambiarPassword() {
        return "cambiarPassword";
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Mapping de cambiar contraseña admin y gestor

    @PostMapping("/cambiar-password")
    public String actualizarPassword(
            Authentication authentication,
            @RequestParam("actual") String actual,
            @RequestParam("nueva") String nueva,
            @RequestParam("repetir") String repetir,
            Model model) {

        Usuario usuario = (Usuario) authentication.getPrincipal();

        // 1. comprobar contraseña actual
        if (!passwordEncoder.matches(actual, usuario.getPassword())) {
            model.addAttribute("error", "La contraseña actual no es correcta");
            return "cambiarPassword";
        }

        // 2. comprobar repetición
        if (!nueva.equals(repetir)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "cambiarPassword";
        }

        // 3. guardar nueva encriptada
        usuario.setPassword(passwordEncoder.encode(nueva));
        usuarioRepository.save(usuario);

        model.addAttribute("success", "Contraseña actualizada correctamente");
        return "cambiarPassword";
    }

    @GetMapping("/cambiar-passwordcol")
    public String mostrarCambiarPasswordColaborador() {

        return "collaborator/cambiarPasswordcol";
    }

    // Mapping de cambiar contraseña para collaborator
    @PostMapping("/cambiar-passwordcol")
    public String actualizarPasswordcol(
            Authentication authentication,
            @RequestParam("actual") String actual,
            @RequestParam("nueva") String nueva,
            @RequestParam("repetir") String repetir,
            Model model) {

        Usuario usuario = (Usuario) authentication.getPrincipal();

        // 1. comprobar contraseña actual
        if (!passwordEncoder.matches(actual, usuario.getPassword())) {
            model.addAttribute("error", "La contraseña actual no es correcta");
            return "collaborator/cambiarPasswordcol";
        }

        // 2. comprobar repetición
        if (!nueva.equals(repetir)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "collaborator/cambiarPasswordcol";
        }

        // 3. guardar nueva encriptada
        usuario.setPassword(passwordEncoder.encode(nueva));
        usuarioRepository.save(usuario);

        model.addAttribute("success", "Contraseña actualizada correctamente");

        return "collaborator/cambiarPasswordcol";
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

    @GetMapping("/menucol")
    public String menucol(Model model, Authentication authentication) {
        listarProyectos(model, authentication);
        return "collaborator/menucol";
    }
}