package com.todo.vlc.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.vlc.model.Proyecto;
import com.todo.vlc.model.Usuario;
import com.todo.vlc.model.UsuarioProyecto;
import com.todo.vlc.model.UsuarioProyectoId;

import jakarta.transaction.Transactional;

public interface UsuarioProyectoRepository extends JpaRepository<UsuarioProyecto, UsuarioProyectoId> {

    List<UsuarioProyecto> findByUsuario(Usuario usuario);

    List<UsuarioProyecto> findByProyecto(Proyecto proyecto);

    Optional<UsuarioProyecto> findByProyectoAndUsuario(Proyecto proyecto, Usuario usuario);

    boolean existsByUsuarioAndProyecto(Usuario usuario, Proyecto proyecto);

    @Transactional
    void deleteByUsuarioAndProyecto(Usuario usuario, Proyecto proyecto);
}