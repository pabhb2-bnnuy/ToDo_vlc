package com.todo.vlc.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.vlc.model.Proyecto;
import com.todo.vlc.model.Usuario;
import com.todo.vlc.model.UsuarioProyecto;
import com.todo.vlc.model.UsuarioProyectoId;

public interface UsuarioProyectoRepository extends JpaRepository<UsuarioProyecto, UsuarioProyectoId> {

    List<UsuarioProyecto> findByUsuario(Usuario usuario);

    List<UsuarioProyecto> findByProyecto(Proyecto proyecto);

    boolean existsByUsuarioAndProyecto(Usuario usuario, Proyecto proyecto);

    void deleteByUsuarioAndProyecto(Usuario usuario, Proyecto proyecto);
}