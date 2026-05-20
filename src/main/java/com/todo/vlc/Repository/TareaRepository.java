package com.todo.vlc.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.vlc.model.Proyecto;
import com.todo.vlc.model.Tarea;
import com.todo.vlc.model.Usuario;

public interface TareaRepository extends JpaRepository<Tarea, Integer> {

    List<Tarea> findByUsuario(Usuario usuario);

    List<Tarea> findByProyecto(Proyecto proyecto);

    List<Tarea> findByEstado(String estado);

    List<Tarea> findByPrioridad(Integer prioridad);

    List<Tarea> findByProyectoAndEstado(Proyecto proyecto, String estado);

    List<Tarea> findByUsuarioAndEstado(Usuario usuario, String estado);
}