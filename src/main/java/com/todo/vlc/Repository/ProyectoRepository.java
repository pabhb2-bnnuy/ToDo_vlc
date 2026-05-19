package com.todo.vlc.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.vlc.model.Proyecto;
import com.todo.vlc.model.Usuario;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {

    List<Proyecto> findByUsuario(Usuario usuario);
    Optional<Proyecto> findByIdproyecto(int idproyecto);


}