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

    /**
     * Trae el Usuario junto con sus UsuarioRol y cada Rol,
     * usando JOIN FETCH para inicializar la colección en la misma consulta.
     */
    @Query("""
        SELECT DISTINCT u
        FROM usuarios u
          JOIN FETCH u.usuarioRoles ur
          JOIN FETCH ur.rol r
        WHERE u.correo = :correo
    """)
    Optional<Usuario> findByCorreoWithRoles(@Param("correo") String correo);

    /**
     * Búsqueda derivada que navega por la colección intermedia:
     * usuarioRoles → rol → nombre
     */
    List<Usuario> findByUsuarioRoles_Rol_Nombre(String rolNombre);

    /**
     * Mismo JPQL que el derivado de arriba, si prefieres escribirlo explícito.
     */
    @Query("""
        SELECT DISTINCT u
        FROM usuarios u
          JOIN u.usuarioRoles ur
          JOIN ur.rol r
        WHERE r.nombre = :rolNombre
    """)
    List<Usuario> findByRolNombre(@Param("rolNombre") String rolNombre);
}

