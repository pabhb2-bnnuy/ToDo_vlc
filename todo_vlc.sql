DROP DATABASE IF EXISTS toDo_vlc;
CREATE DATABASE toDo_vlc;
USE toDo_vlc;

CREATE TABLE usuarios ( 
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    email VARCHAR(100) UNIQUE,
    passwrd VARCHAR(200) NOT NULL,
    rol VARCHAR(50),
    fecha_creacion VARCHAR(200)
);


CREATE TABLE proyecto (
    id_proyecto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    descripcion VARCHAR(150),
    fecha_inicio DATE,
    fecha_limite DATE,
    estado VARCHAR(30),
     id_usuario INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

CREATE TABLE tareas (
    id_tarea INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150),
    descripcion VARCHAR(150),
    prioridad INT,
    fecha_asignacion VARCHAR(16) DEFAULT (DATE_FORMAT(CURRENT_TIMESTAMP, '%d/%m/%Y %H:%i')),
    estado VARCHAR(30)
);

CREATE TABLE usuario_proyecto (
    id_usuario INT,
    id_proyecto INT,
    fecha_asignacion VARCHAR(16) DEFAULT (DATE_FORMAT(CURRENT_TIMESTAMP, '%d/%m/%Y %H:%i')),
    PRIMARY KEY (id_usuario, id_proyecto),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_proyecto) REFERENCES proyecto(id_proyecto) ON DELETE CASCADE
);