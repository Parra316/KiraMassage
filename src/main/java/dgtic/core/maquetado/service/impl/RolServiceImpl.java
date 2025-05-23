package dgtic.core.maquetado.service.impl;

import dgtic.core.maquetado.model.Rol;
import dgtic.core.maquetado.repository.RolRepository;
import dgtic.core.maquetado.service.RolService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolServiceImpl extends GenericServiceImpl<Rol, Integer> implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    JpaRepository<Rol, Integer> getRepository() {
        return rolRepository;
    }

    /** Reemplaza tu implementación actual por esta */
    @Override
    @Transactional(readOnly = true)
    public Rol getRolConUsuarios(Integer id) {
        return rolRepository.findByIdWithUsuarios(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Rol no encontrado con id: " + id)
                );
    }

    /** Si quieres que findAll() te traiga siempre los usuarios, sobreescríbelo */
    @Override
    public List<Rol> findAll() {
        return rolRepository.findAllWithUsuarios();
    }

    @Override
    @Transactional
    public Rol update(Integer id, Rol entity) {
        return rolRepository.findById(id)
                .map(existing -> {
                    existing.setNombre(entity.getNombre());
                    existing.setDescripcion(entity.getDescripcion());
                    existing.setUsuarioRoles(entity.getUsuarioRoles());
                    return rolRepository.save(existing);
                })
                .orElseThrow(() ->
                        new RuntimeException("Rol no encontrado con id: " + id)
                );
    }
}
