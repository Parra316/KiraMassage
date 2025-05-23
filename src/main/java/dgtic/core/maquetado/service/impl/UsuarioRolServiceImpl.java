package dgtic.core.maquetado.service.impl;

import dgtic.core.maquetado.model.UsuarioRol;
import dgtic.core.maquetado.model.UsuarioRolId;
import dgtic.core.maquetado.repository.UsuarioRolRepository;
import dgtic.core.maquetado.service.UsuarioRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioRolServiceImpl extends GenericServiceImpl<UsuarioRol, UsuarioRolId> implements UsuarioRolService {

    @Autowired
    UsuarioRolRepository usuarioRolRepository;

    @Override
    JpaRepository<UsuarioRol, UsuarioRolId> getRepository() {
        return usuarioRolRepository;
    }

    @Override
    @Transactional
    public void deleteUsuarioRol(Integer userId, Integer rolId) {
        usuarioRolRepository.deleteByUser_IdUsuarioAndRol_IdRol(userId, rolId);
    }

    @Override
    public UsuarioRol save(UsuarioRol usuarioRol) {
        return usuarioRolRepository.save(usuarioRol);
    }
}
