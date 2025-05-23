DROP DATABASE IF EXISTS proyecto;
CREATE DATABASE IF NOT EXISTS proyecto;
USE proyecto;

CREATE TABLE IF NOT EXISTS usuarios (
    usuario_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    registro DATETIME DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS roles (
    rol_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL,
    descripcion TEXT DEFAULT NULL
);


CREATE TABLE IF NOT EXISTS usuarioroles (
    usuario_id INT,
    rol_id INT,
    PRIMARY KEY (usuario_id, rol_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(usuario_id) ON DELETE CASCADE,
    FOREIGN KEY (rol_id) REFERENCES roles(rol_id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS servicio (
    servicio_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    duracion INT NOT NULL, -- en minutos
    precio DECIMAL(10,2) NOT NULL,
    estatus ENUM('disponible', 'nodisponible') DEFAULT 'disponible'
);


CREATE TABLE IF NOT EXISTS consultorio (
    consultorio_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion TEXT NOT NULL,
    telefono VARCHAR(20)
);


CREATE TABLE IF NOT EXISTS horario (
    horario_id INT AUTO_INCREMENT PRIMARY KEY,
    consultorio_id INT,
    fecha DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    FOREIGN KEY (consultorio_id) REFERENCES consultorio(consultorio_id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS disponibilidad (
    disponibilidad_id INT AUTO_INCREMENT PRIMARY KEY,
    consultorio_id INT,
    servicio_id INT,
    FOREIGN KEY (consultorio_id) REFERENCES consultorio(consultorio_id) ON DELETE CASCADE,
    FOREIGN KEY (servicio_id) REFERENCES servicio(servicio_id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS cita (
    cita_id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    masajista_id INT,
    servicio_id INT,
    consultorio_id INT,
    horario_id INT,
    fecha_cita DATETIME NOT NULL,
    estatus ENUM('programado', 'cancelado', 'completado') DEFAULT 'programado',
    FOREIGN KEY (usuario_id) REFERENCES usuarios(usuario_id) ON DELETE CASCADE,
    FOREIGN KEY (masajista_id) REFERENCES usuarios(usuario_id) ON DELETE SET NULL,
    FOREIGN KEY (servicio_id) REFERENCES servicio(servicio_id) ON DELETE CASCADE,
    FOREIGN KEY (consultorio_id) REFERENCES consultorio(consultorio_id) ON DELETE CASCADE,
    FOREIGN KEY (horario_id) REFERENCES horario(horario_id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS pago (
    pago_id INT AUTO_INCREMENT PRIMARY KEY,
    cita_id INT UNIQUE,
    monto DECIMAL(10,2) NOT NULL,
    pago_metodo ENUM('efectivo', 'tarjeta_de_credito', 'tarjeta_de_debito', 'paypal', 'transferencia') NOT NULL,
    pago_estatus ENUM('pendiente', 'completada', 'fallida') DEFAULT 'pendiente',
    pago_fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cita_id) REFERENCES cita(cita_id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS resena (
    resena_id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    servicio_id INT,
    calificacion INT NOT NULL CHECK (calificacion BETWEEN 1 AND 5),
    comentario VARCHAR(100),
    fecha_resena DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(usuario_id),
    FOREIGN KEY (servicio_id) REFERENCES servicio(servicio_id)
);