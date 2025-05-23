package dgtic.core.maquetado.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="horario")
public class Horario implements Serializable {
    @Id
    @Column(name = "horario_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHorario;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "consultorio_id")
    private Consultorio horarioConsultorio;
    @Column(name = "fecha")
    @NotNull
    private LocalDate fecha;
    @Column(name = "hora_inicio")
    @NotNull
    private LocalTime horaInicio;
    @Column(name = "hora_fin")
    private LocalTime horaFin;
    @JsonIgnore
    @OneToMany(mappedBy = "citaHorario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cita> citas = new HashSet<>();

}
