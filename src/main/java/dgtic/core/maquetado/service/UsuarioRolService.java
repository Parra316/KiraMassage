package dgtic.core.maquetado.service;

import dgtic.core.maquetado.model.UsuarioRol;
import dgtic.core.maquetado.model.UsuarioRolId;

public interface UsuarioRolService extends GenericService<UsuarioRol, UsuarioRolId>{
    void deleteUsuarioRol(Integer userId, Integer rolId);
    UsuarioRol save(UsuarioRol usuarioRol);

}
