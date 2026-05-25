    DROP DATABASE IF EXISTS todo_vlc;
    CREATE DATABASE todo_vlc;
    USE todo_vlc;

    CREATE TABLE usuarios ( 
        idusuario INT AUTO_INCREMENT PRIMARY KEY,
        nombre VARCHAR(50),
        email VARCHAR(100) UNIQUE,
        passwrd VARCHAR(200) NOT NULL,
        rol VARCHAR(50),
        fecha_creacion VARCHAR(200),
        enabled boolean
    );


    CREATE TABLE proyecto (
        idproyecto INT AUTO_INCREMENT PRIMARY KEY,
        nombre VARCHAR(50),
        descripcion VARCHAR(150),
        fecha_inicio VARCHAR(200),
        fecha_limite DATE,
        estado VARCHAR(30),
        idusuario INT,
        FOREIGN KEY (idusuario) REFERENCES usuarios(idusuario)
    );

    CREATE TABLE tareas (
        idtarea INT AUTO_INCREMENT PRIMARY KEY,
        titulo VARCHAR(150),
        descripcion VARCHAR(150),
        prioridad INT,
        idproyecto INT,
        idusuario INT,
        fecha_asignacion VARCHAR(16) DEFAULT (DATE_FORMAT(CURRENT_TIMESTAMP, '%d/%m/%Y %H:%i')),
        estado VARCHAR(30),
        FOREIGN KEY (idproyecto)
        REFERENCES proyecto(idproyecto)
        ON DELETE CASCADE,
        FOREIGN KEY (idusuario)
        REFERENCES usuarios(idusuario)
    );

    CREATE TABLE usuario_proyecto (
        idusuario INT,
        idproyecto INT,
        fecha_asignacion VARCHAR(16) DEFAULT (DATE_FORMAT(CURRENT_TIMESTAMP, '%d/%m/%Y %H:%i')),
        PRIMARY KEY (idusuario, idproyecto),
        FOREIGN KEY (idusuario) REFERENCES usuarios(idusuario) ON DELETE CASCADE,
        FOREIGN KEY (idproyecto) REFERENCES proyecto(idproyecto) ON DELETE CASCADE
    );

    INSERT INTO usuarios (nombre, email, passwrd, rol, enabled) VALUES ('admin','admin@admin.com', '$2a$10$POeHsb.tHP2P9FU1Yhe0EeKHVk9NOmjSRMx/.Q7krLP05nOYTiVsC', 'ADMIN', true);