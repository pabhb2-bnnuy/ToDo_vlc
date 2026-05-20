package com.todo.vlc.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.vlc.model.Usuario;
import com.todo.vlc.model.Rol;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Usuario> findByEnabledTrue();

    List<Usuario> findByEnabledFalse();

    List<Usuario> findByRol(Rol rol);

    Optional<Usuario> findByEmailAndEnabledTrue(String email);
}