package dgtic.core.maquetado.repository;

import dgtic.core.maquetado.model.UsuarioRol;
import dgtic.core.maquetado.model.UsuarioRolId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, UsuarioRolId> {
    void deleteByUser_IdUsuarioAndRol_IdRol(Integer userId, Integer rolId);
}
