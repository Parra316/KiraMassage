package dgtic.core.maquetado.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="consultorio")
public class Consultorio implements Serializable {
    @Id
    @Column(name = "consultorio_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idConsultorio;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private String telefono;
    @JsonIgnore
    @OneToMany(mappedBy = "horarioConsultorio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Horario> horarios = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "dispoConsultorio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Disponibilidad> disponibles = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "citaConsultorio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cita> citas = new HashSet<>();
}