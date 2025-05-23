package dgtic.core.maquetado.service;

import dgtic.core.maquetado.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService extends GenericService<Usuario, Integer>{
    List<Usuario> findAllAdmin();
    List<Usuario> findAllClients();
    Optional<Usuario> findByCorreo(String correo);
}
