package com.todo.vlc.model;

import java.io.Serializable;
import lombok.*;

import jakarta.persistence.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UsuarioProyectoId implements Serializable {

    private Integer idUsuario;
    private Integer idProyecto;
}