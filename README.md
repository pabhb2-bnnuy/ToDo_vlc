# ToDo vlc - Feria Valencia - La Sénia

<p align="center">

[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![Nginx](https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white)](https://nginx.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

</p>

<p align="left">
  Despliegue completo utilizando Docker Compose, Nginx y Spring Boot, Maven.
</p>

---

#  Descripción

Este proyecto incluye una configuración lista para ejecutar una aplicación Spring Boot mediante contenedores Docker, utilizando Nginx como reverse proxy.

Estructurado para iniciar facilmente con Docker Compose.

Completamente configurable mediante los archivos como .env, nginx.conf, docker-compose.yml...

---
#  Administrador

Por defecto siempre habra un usuario administrador con las siguientes credenciales para iniciar sesión:
```text
Email: admin@admin.com
Contraseña: admin
```
---

#  Requisitos

Antes de comenzar asegúrate de tener Docker instalado.

Puedes comprobarlo ejecutando:

```bash
docker --version
```

---

# Instalación

## 1. Descargar la release

Descarga el archivo `.zip` desde la sección de Releases del repositorio.



## 2. Descomprimir el proyecto

```bash
unzip release_v1.zip
```



## 3. Acceder a la carpeta del proyecto

```bash
cd release
```



## 4. Arrancamos docker, los contenedores

```bash
docker compose up -d --build
```

Docker descargará automáticamente las imágenes necesarias y construirá los servicios del proyecto.

---

# Acceso

Una vez iniciado correctamente, la aplicación estará disponible localmente en:

```txt
http://localhost
```

---

# Estructura del proyecto
(En base al release)

```txt
release/
├── compiled/
│   └── todo_vlc.jar
├── docker-compose.yml
├── .env
├── Dockerfile
├── todo_vlc.sql
└── nginx.conf
```

---

# Autores

[![Pablo Hermosilla](https://img.shields.io/badge/Pablo%20Hermosilla-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/pabhb2-bnnuy)

[![Nico Adria](https://img.shields.io/badge/Nico%20Adria-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Nicoaz25)
