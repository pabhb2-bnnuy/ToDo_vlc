package com.todo.vlc.model;

public class Usuario_proyecto {

    private int id_usuario;
    private int id_proyecto;
    private String fecha_asignacion;

    public Usuario_proyecto(int id_usuario, int id_proyecto, String fecha_asignacion) {
        this.id_usuario = id_usuario;
        this.id_proyecto = id_proyecto;
        this.fecha_asignacion = fecha_asignacion;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_proyecto() {
        return id_proyecto;
    }

    public void setId_proyecto(int id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    public String getFecha_asignacion() {
        return fecha_asignacion;
    }

    public void setFecha_asignacion(String fecha_asignacion) {
        this.fecha_asignacion = fecha_asignacion;
    }

}