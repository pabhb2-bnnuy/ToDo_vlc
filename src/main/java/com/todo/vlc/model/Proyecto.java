package com.todo.vlc.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "proyecto")
@Getter
@Setter
@NoArgsConstructor
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_proyecto;
    private String nombre;
    private String descripcion;
    @Column(insertable = false, updatable = false)
    private LocalDateTime fecha_inicio;
    private String fecha_limite;
    private String estado;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}