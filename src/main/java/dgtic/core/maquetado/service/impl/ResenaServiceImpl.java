package dgtic.core.maquetado.service.impl;

import dgtic.core.maquetado.model.Resena;
import dgtic.core.maquetado.repository.ResenaRepository;
import dgtic.core.maquetado.service.ResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResenaServiceImpl extends GenericServiceImpl<Resena, Integer> implements ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    @Override
    JpaRepository<Resena, Integer> getRepository() {
        return resenaRepository;
    }

    @Override
    @Transactional
    public Resena update(Integer id, Resena entity) {
        return resenaRepository.findById(id).map(entityExistente -> {
            entityExistente.setResenaUsuario(entity.getResenaUsuario());
            entityExistente.setResenaServicio(entity.getResenaServicio());
            entityExistente.setCalificacion(entity.getCalificacion());
            entityExistente.setComentario(entity.getComentario());
            entityExistente.setFecha(entity.getFecha());
            return resenaRepository.save(entityExistente);
        }).orElseThrow(() -> new RuntimeException("Resena no encontrada con id: " + id));
    }
}
