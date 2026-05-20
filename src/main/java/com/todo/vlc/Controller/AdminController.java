import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.todo.vlc.Repository.ProyectoRepository;
import com.todo.vlc.Repository.UsuarioRepository;
import com.todo.vlc.Repository.TareaRepository;


@Controller
public class AdminController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private TareaRepository tareaRepository;

    @GetMapping("/admin")
    public String admin(Model model) {

        model.addAttribute("usuarios",
                usuarioRepository.findAll());

        model.addAttribute("proyectos",
                proyectoRepository.findAll());

        model.addAttribute("tareas",
                tareaRepository.findAll());

        model.addAttribute("totalUsuarios",
                usuarioRepository.count());

        model.addAttribute("totalProyectos",
                proyectoRepository.count());

        model.addAttribute("totalTareas",
                tareaRepository.count());

        return "admin";
    }
}