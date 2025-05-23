package dgtic.core.maquetado.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="cita")
public class Cita {
    @Id
    @Column(name = "cita_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCita;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "usuario_id")
    private Usuario citaUsuario;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "masajista_id")
    private Usuario citaMasajista;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "servicio_id")
    private Servicio citaServicio;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "consultorio_id")
    private Consultorio citaConsultorio;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "horario_id")
    private Horario citaHorario;
    @Column(name = "fecha_cita")
    private LocalDate fechaCita;
    @Column(name = "estatus")
    private String estatus;
    @JsonIgnore
    @OneToMany(mappedBy = "pagoCita", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Pago> pagos = new HashSet<>();
}

