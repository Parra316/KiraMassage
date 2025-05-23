package dgtic.core.maquetado.service;

import dgtic.core.maquetado.model.Usuario;

import java.util.List;

public interface UsuarioService extends GenericService<Usuario, Integer>{
    List<Usuario> findAllAdmin();
    List<Usuario> findAllClients();
}
