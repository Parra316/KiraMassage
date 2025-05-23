# Kira Massage

Un sistema web para la gestión integral de un consultorio de masajes, desarrollado con Spring Boot, Thymeleaf, JPA (MariaDB) y Spring Security (JWT).

---

## 📋 Funcionalidades principales

1. **Autenticación y seguridad**  
   - Registro y login basado en base de datos con JWT.  
   - Filtrado de rutas públicas (`/auth/**`, CSS/JS) y protegidas.  
   - Carga de roles con _fetch join_ para evitar errores de carga perezosa.

2. **Gestión de usuarios**  
   - Clientes, masajistas y administradores.  
   - CRUD de usuarios mediante formularios Thymeleaf y validaciones.

3. **Asignación de roles**  
   - Entidad intermedia `UsuarioRol` con clave compuesta (`@IdClass`).  
   - Página `/role/manage` para ver, agregar y eliminar usuarios de cada rol vía AJAX.

4. **Configuración de consultorios y servicios**  
   - Tablas `Consultorio`, `Servicio` y unión `Disponibilidad`.  
   - Módulo para vincular qué servicios ofrece cada consultorio.

5. **Agendamiento de citas**  
   - Entidad `Cita` con fecha (`LocalDate`) y relación a usuario, masajista, consultorio y servicio.  
   - Formulario de creación, edición en modals y eliminación en tabla paginada.

---

## 📦 Tecnologías

- **Java 17**, Spring Boot 3.x  
- **Spring Data JPA** + MariaDB  
- **Spring Security (JWT)**  
- **Thymeleaf** + Bootstrap 5 + Vanilla JS  
- **Lombok**

---

## 🚀 Instalación y puesta en marcha

1. **Clona el repositorio**  
   ```bash
   git clone git@github.com:TU_USUARIO/kira-massage.git
   cd kira-massage

# Configura application.properties

spring.datasource.url=jdbc:mariadb://localhost:3306/kiramassage
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password

# JWT
jwt.secret=TU_SECRETO_LARGO
jwt.expirationMs=3600000

