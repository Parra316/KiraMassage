package dgtic.core.maquetado.service.impl;

import dgtic.core.maquetado.model.Rol;
import dgtic.core.maquetado.repository.RolRepository;
import dgtic.core.maquetado.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolServiceImpl extends GenericServiceImpl<Rol, Integer> implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    JpaRepository<Rol, Integer> getRepository() {
        return rolRepository;
    }

    @Transactional
    public Rol getRolConUsuarios(Integer id) {
        Rol rol = rolRepository.findById(id).orElseThrow();
        rol.getUsuarios().size(); // Forzamos la carga de la colección
        return rol;
    }

    @Override
    @Transactional
    public Rol update(Integer id, Rol entity) {
        return rolRepository.findById(id).map(entityExistente -> {
            entityExistente.setNombre(entity.getNombre());
            entityExistente.setDescripcion(entity.getDescripcion());
            entityExistente.setUsuarios(entity.getUsuarios());
            return rolRepository.save(entityExistente);
        }).orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));
    }
}
