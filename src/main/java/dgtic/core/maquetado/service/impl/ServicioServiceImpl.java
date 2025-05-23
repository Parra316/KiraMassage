package dgtic.core.maquetado.service.impl;

import dgtic.core.maquetado.model.*;
import dgtic.core.maquetado.repository.ServicioRepository;
import dgtic.core.maquetado.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class ServicioServiceImpl extends GenericServiceImpl<Servicio, Integer> implements ServicioService {

    @Autowired
    ServicioRepository servicioRepository;

    @Override
    JpaRepository<Servicio, Integer> getRepository() {
        return servicioRepository;
    }

    @Override
    @Transactional
    public Servicio update(Integer id, Servicio entity) {
        return servicioRepository.findById(id)
            .map(entityExistente -> {
                // Actualiza campos sencillos
                entityExistente.setNombre(entity.getNombre());
                entityExistente.setDescripcion(entity.getDescripcion());
                entityExistente.setDuracion(entity.getDuracion());
                entityExistente.setPrecio(entity.getPrecio());
                entityExistente.setEstatus(entity.getEstatus());

                // --- Disponibilidades ---
                Set<Disponibilidad> nuevasDisponibles = entity.getDisponibles();
                entityExistente.getDisponibles().clear();
                if (nuevasDisponibles != null) {
                    for (Disponibilidad d : nuevasDisponibles) {
                        d.setDispoServicio(entityExistente);
                        entityExistente.getDisponibles().add(d);
                    }
                }

                // --- Citas ---
                Set<Cita> nuevasCitas = entity.getCitas();
                entityExistente.getCitas().clear();
                if (nuevasCitas != null) {
                    for (Cita c : nuevasCitas) {
                        c.setCitaServicio(entityExistente);
                        entityExistente.getCitas().add(c);
                    }
                }

                // --- Resena ---
                Set<Resena> nuevasResena = entity.getResenas();
                entityExistente.getResenas().clear();
                if (nuevasResena != null) {
                    for (Resena r : nuevasResena) {
                        r.setResenaServicio(entityExistente);
                        entityExistente.getResenas().add(r);
                    }
                }

                return servicioRepository.save(entityExistente);
        }).orElseThrow(() -> new RuntimeException(
                "Servicio no encontrado con id: " + id));
    }
}
