package dgtic.core.maquetado.repository;

import dgtic.core.maquetado.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("""
        SELECT u
        FROM usuarios u
        LEFT JOIN FETCH u.roles ur
        LEFT JOIN FETCH ur.rol r
        WHERE u.correo = :correo
      """)
    Optional<Usuario> findByCorreoWithRoles(@Param("correo") String correo);
    List<Usuario> findByRolesRolNombre(String nombre);
    Optional<Usuario> findByCorreo(String correo);

}
