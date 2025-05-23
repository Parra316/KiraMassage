# 🧴 KiraMassage - Sistema de Gestión de Masajes

KiraMassage es una aplicación web desarrollada con **Spring Boot**, **Thymeleaf**, **JPA** y **Spring Security (JWT)**. Permite a los usuarios agendar citas para masajes y a los administradores gestionar servicios, consultorios, usuarios, citas, disponibilidad y roles.

---

## 🗂️ Índice

- [[#🔑 Características Principales]]
- [[#⚙️ Requisitos Técnicos]]
- [[#🛠️ Configuración Inicial]]
- [[#📁 Estructura del Proyecto]]
- [[#👤 Roles y Funcionalidades]]
- [[#📸 Capturas (opcional)]]
- [[#📌 Créditos]]

---

## 🔑 Características Principales

### Usuarios (USER)
- Inicio de sesión.
- Vista de servicios disponibles.
- Agendamiento de citas (consultorio, masaje, horario).

### Administradores (ADMIN)
- Panel de gestión de:
  - Servicios (alta/baja/modificación).
  - Consultorios.
  - Citas agendadas.
  - Usuarios.
  - Disponibilidad por consultorio.
  - Roles y permisos de usuarios.

---

## ⚙️ Requisitos Técnicos

- **Java** 17 o superior.
- **Maven** 3.8+
- **MySQL** 8.x

---

## 🛠️ Configuración Inicial

### 1. Clonar el Repositorio

## bash
git clone https://github.com/Parra316/KiraMassage.git
cd KiraMassage


### 2. Crear la Base de Datos

## Cargar los archivos SQL desde src/main/resources/sql/:
        schema.sql: Crea las tablas.
        data.sql: Inserta roles y usuarios por defecto. ⚠️ Este paso es obligatorio para el correcto funcionamiento del login

# Configura application.properties

spring.datasource.url=jdbc:mariadb://localhost:3306/proyecto
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password

# JWT
jwt.secret=TU_SECRETO_LARGO
jwt.expirationMs=3600000

