package dgtic.core.maquetado.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="resena")
public class Resena {
    @Id
    @Column(name = "resena_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idResena;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "usuario_id")
    private Usuario resenaUsuario;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "servicio_id")
    private Servicio resenaServicio;
    @Column(name = "calificacion")
    @Range(min=1,max = 5)
    private Integer calificacion;
    @Column(name = "comentario")
    private String comentario;
    @Column(name = "fecha_resena")
    private LocalDateTime fecha;
}
