package com.todo.vlc.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "proyecto")
@Getter
@Setter
@NoArgsConstructor
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idproyecto;
    private String nombre;
    private String descripcion;
    @Column(insertable = false, updatable = false)
    private LocalDateTime fecha_inicio;
    private String fecha_limite;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario usuario;
}