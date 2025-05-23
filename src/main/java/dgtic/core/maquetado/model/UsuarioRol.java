package dgtic.core.maquetado.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "usuarioroles")
@IdClass(UsuarioRolId.class)
public class UsuarioRol implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private Usuario user;

    @Id
    @ManyToOne
    @JoinColumn(name = "rol_id")
    @JsonIgnore
    private Rol rol;
}
