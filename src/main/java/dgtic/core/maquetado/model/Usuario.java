package dgtic.core.maquetado.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "usuarios")
public class Usuario implements Serializable {
    @Id
    @Column(name = "usuario_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;
    @Column(name = "nombre")
    @NotNull
    @NotBlank(message = "El Nombre no puede ser vacio")
    private String nombre;
    @Column(name = "apellido_materno")
    @NotNull
    @NotBlank(message = "El apellido Materno no puede ser vacio")
    private String apellidoMaterno;
    @Column(name = "apellido_paterno")
    @NotNull
    @NotBlank(message = "El apellido Paterno no puede ser vacio")
    private String apellidoPaterno;
    @Column(name = "edad")
    @NotNull
    @Min(1)
    @Max(99)
    private Integer edad;
    @Column(name = "correo")
    @Email
    @NotNull
    @NotBlank(message = "El correo no puede ser vacio")
    private String correo;
    @Column(name = "contrasena")
    @NotNull
    @NotBlank(message = "La contrasena no puede ser vacia")
    private String contrasena;
    @Column(name = "telefono")
    @Size(min = 8, max = 10)
    @NotNull
    @NotBlank(message = "El telefono no puede ser vacio")
    private String telefono;
    @Column(name = "registro")
    private LocalDateTime registro;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsuarioRol> usuarioRoles = new HashSet<>();
    @OneToMany(mappedBy = "citaUsuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Cita> citaUser = new HashSet<>();
    @OneToMany(mappedBy = "citaMasajista", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Cita> citaAdmin = new HashSet<>();

    public void addRol(Rol rol) {
        UsuarioRol ur = new UsuarioRol(this, rol);
        usuarioRoles.add(ur);
        rol.getUsuarioRoles().add(ur);
    }
}

