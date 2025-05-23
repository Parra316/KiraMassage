package dgtic.core.maquetado.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="servicio")
public class Servicio implements Serializable {
    @Id
    @Column(name = "servicio_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idServicio;
    @Column(name = "nombre")
    @NotNull
    @NotBlank(message = "El nombre no puede ser vacia")
    private String nombre;
    @Column(name = "descripcion")
    @NotNull
    @NotBlank(message = "La descripcion no puede ser vacia")
    private String descripcion;
    @Column(name = "duracion")
    @Range(min=45,max = 90)
    @NotNull
    private Integer duracion;
    @Column(name = "precio")
    @Range(min=450,max = 1000)
    @NotNull
    private float precio;
    @Column(name = "estatus")
    @NotNull
    @NotBlank(message = "El estatus no puede ser vacia")
    private String estatus;
    @JsonIgnore
    @OneToMany(mappedBy = "dispoServicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Disponibilidad> disponibles = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "citaServicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cita> citas = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "resenaServicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Resena> resenas = new HashSet<>();
}
