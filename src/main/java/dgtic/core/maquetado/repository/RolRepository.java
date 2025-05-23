package dgtic.core.maquetado.repository;

import dgtic.core.maquetado.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    /**
     * Trae un Rol con sus UsuarioRol y cada Usuario, todo en una sola consulta.
     */
    @Query("""
    SELECT DISTINCT r
    FROM roles r
      LEFT JOIN FETCH r.usuarioRoles ur
      LEFT JOIN FETCH ur.user u
    WHERE r.idRol = :id
  """)
    Optional<Rol> findByIdWithUsuarios(@Param("id") Integer id);

    /**
     * Ya tenías este para el findAll:
     */
    @Query("""
    SELECT DISTINCT r
    FROM roles r
      LEFT JOIN FETCH r.usuarioRoles ur
      LEFT JOIN FETCH ur.user u
  """)
    List<Rol> findAllWithUsuarios();
}

