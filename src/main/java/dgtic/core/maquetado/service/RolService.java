package dgtic.core.maquetado.service;

import dgtic.core.maquetado.model.Rol;

public interface RolService extends GenericService<Rol, Integer> {
    Rol getRolConUsuarios(Integer id);
}

