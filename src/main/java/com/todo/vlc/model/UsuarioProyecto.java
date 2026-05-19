package com.todo.vlc.model;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario_proyecto")
@Getter
@Setter
@NoArgsConstructor
public class UsuarioProyecto {

    @EmbeddedId
    private UsuarioProyectoId id;

    @ManyToOne
    @MapsId("idUsuario")
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    @ManyToOne
    @MapsId("idProyecto")
    @JoinColumn(name = "idproyecto")
    private Proyecto proyecto;

    @Column(insertable = false, updatable = false)
    private String fecha_asignacion;
}