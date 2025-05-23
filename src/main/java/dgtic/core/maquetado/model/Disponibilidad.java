package dgtic.core.maquetado.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="disponibilidad")
public class Disponibilidad implements Serializable {
    @Id
    @Column(name = "disponibilidad_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDisponibilidad;
    @JoinColumn(name = "consultorio_id")
    @ManyToOne
    @JsonIgnore
    private Consultorio dispoConsultorio;
    @JoinColumn(name = "servicio_id")
    @ManyToOne
    @JsonIgnore
    private Servicio dispoServicio;
}
