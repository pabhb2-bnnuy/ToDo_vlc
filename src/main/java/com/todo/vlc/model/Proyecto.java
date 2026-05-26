package com.todo.vlc.model;

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
    private String fechainicio;
    private String fechalimite;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario usuario;
}