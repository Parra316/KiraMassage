package dgtic.core.maquetado.service.impl;

import dgtic.core.maquetado.model.Cita;
import dgtic.core.maquetado.model.Consultorio;
import dgtic.core.maquetado.model.Disponibilidad;
import dgtic.core.maquetado.model.Horario;
import dgtic.core.maquetado.repository.ConsultorioRepository;
import dgtic.core.maquetado.service.ConsultorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class ConsultorioServiceImpl extends GenericServiceImpl<Consultorio, Integer> implements ConsultorioService {
    @Autowired
    ConsultorioRepository consultorioRepository;

    @Override
    protected JpaRepository<Consultorio, Integer> getRepository() {
        return consultorioRepository;
    }

    @Override
    @Transactional
    public Consultorio update(Integer id, Consultorio entity) {
        return consultorioRepository.findById(id)
                .map(entityExistente -> {
                    // Actualiza campos sencillos
                    entityExistente.setNombre(entity.getNombre());
                    entityExistente.setDireccion(entity.getDireccion());
                    entityExistente.setTelefono(entity.getTelefono());

                    // --- Horarios ---
                    Set<Horario> nuevosHorarios = entity.getHorarios();
                    entityExistente.getHorarios().clear();
                    if (nuevosHorarios != null) {
                        for (Horario h : nuevosHorarios) {
                            h.setHorarioConsultorio(entityExistente);
                            entityExistente.getHorarios().add(h);
                        }
                    }

                    // --- Disponibilidades ---
                    Set<Disponibilidad> nuevasDisponibles = entity.getDisponibles();
                    entityExistente.getDisponibles().clear();
                    if (nuevasDisponibles != null) {
                        for (Disponibilidad d : nuevasDisponibles) {
                            d.setDispoConsultorio(entityExistente);
                            entityExistente.getDisponibles().add(d);
                        }
                    }

                    // --- Citas ---
                    Set<Cita> nuevasCitas = entity.getCitas();
                    entityExistente.getCitas().clear();
                    if (nuevasCitas != null) {
                        for (Cita c : nuevasCitas) {
                            c.setCitaConsultorio(entityExistente);
                            entityExistente.getCitas().add(c);
                        }
                    }

                    return consultorioRepository.save(entityExistente);
        }).orElseThrow(() -> new RuntimeException(
                        "Consultorio no encontrado con id: " + id));
    }

}
