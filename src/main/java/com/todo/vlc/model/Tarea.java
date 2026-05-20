package com.todo.vlc.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tareas")
@Getter
@Setter
@NoArgsConstructor
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idtarea;
    private String titulo;
    private String descripcion;
    private int prioridad;
    private String fecha_entrega;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "idproyecto")
    private Proyecto proyecto;

    @ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

}