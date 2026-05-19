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
    private int id_tarea;
    private String titulo;
    private String descripcion;
    private int prioridad;
    private String fecha_entrega;
    private String estado;

}