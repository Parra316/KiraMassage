package dgtic.core.maquetado.service;

import dgtic.core.maquetado.model.UsuarioRol;
import dgtic.core.maquetado.model.UsuarioRolId;

public interface UsuarioRolService extends GenericService<UsuarioRol, UsuarioRolId>{
    UsuarioRol save(UsuarioRol usuarioRol);
}
