package dgtic.core.maquetado.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="pago")
public class Pago {
    @Id
    @Column(name = "pago_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPago;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cita_id")
    private Cita pagoCita;
    @Column(name = "monto")
    @Range(min=450,max = 1000)
    @NotNull
    private float monto;
    @Column(name = "pago_metodo")
    @NotNull
    private String metodo;
    @Column(name = "pago_estatus")
    @NotNull
    @NotBlank(message = "El estatus no puede ser vacia")
    private String estatus;
    @Column(name = "pago_fecha")
    @NotNull
    private LocalDateTime fecha;
}
