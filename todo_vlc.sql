DROP DATABASE IF EXISTS toDo_vlc;
CREATE DATABASE toDo_vlc;
USE toDo_vlc;

CREATE TABLE usuarios ( 
    idusuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    email VARCHAR(100) UNIQUE,
    passwrd VARCHAR(200) NOT NULL,
    rol VARCHAR(50),
    fecha_creacion VARCHAR(200)
);


CREATE TABLE proyecto (
    idproyecto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    descripcion VARCHAR(150),
    fecha_inicio VARCHAR(200),
    fecha_limite DATE,
    estado VARCHAR(30),
     idusuario INT NOT NULL,
    FOREIGN KEY (idusuario) REFERENCES usuarios(idusuario)
);

CREATE TABLE tareas (
    idtarea INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150),
    descripcion VARCHAR(150),
    prioridad INT,
    fecha_asignacion VARCHAR(16) DEFAULT (DATE_FORMAT(CURRENT_TIMESTAMP, '%d/%m/%Y %H:%i')),
    estado VARCHAR(30)
);

CREATE TABLE usuario_proyecto (
    idusuario INT,
    idproyecto INT,
    fecha_asignacion VARCHAR(16) DEFAULT (DATE_FORMAT(CURRENT_TIMESTAMP, '%d/%m/%Y %H:%i')),
    PRIMARY KEY (idusuario, idproyecto),
    FOREIGN KEY (idusuario) REFERENCES usuarios(idusuario) ON DELETE CASCADE,
    FOREIGN KEY (idproyecto) REFERENCES proyecto(idproyecto) ON DELETE CASCADE
);