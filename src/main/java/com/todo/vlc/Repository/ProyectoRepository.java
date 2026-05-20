package com.todo.vlc.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.vlc.model.Proyecto;
import com.todo.vlc.model.Usuario;

public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {

    List<Proyecto> findByUsuario(Usuario usuario);

    List<Proyecto> findByEstado(String estado);

    List<Proyecto> findByNombreContainingIgnoreCase(String nombre);
}