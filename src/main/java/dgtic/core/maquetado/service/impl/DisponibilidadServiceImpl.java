package dgtic.core.maquetado.service.impl;

import dgtic.core.maquetado.model.Disponibilidad;
import dgtic.core.maquetado.repository.DisponibilidadRepository;
import dgtic.core.maquetado.service.DisponibilidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DisponibilidadServiceImpl extends GenericServiceImpl<Disponibilidad, Integer> implements DisponibilidadService {

    @Autowired
    private DisponibilidadRepository disponibilidadRepository;

    @Override
    JpaRepository<Disponibilidad, Integer> getRepository() {
        return disponibilidadRepository;
    }

    @Override
    @Transactional
    public Disponibilidad update(Integer id, Disponibilidad entity) {
        return disponibilidadRepository.findById(id).map(entityExistente -> {
            entityExistente.setDispoConsultorio(entity.getDispoConsultorio());
            entityExistente.setDispoServicio(entity.getDispoServicio());
            return disponibilidadRepository.save(entityExistente);
        }).orElseThrow(() -> new RuntimeException("Disponibilidad no encontrada con id: " + id));
    }
}
