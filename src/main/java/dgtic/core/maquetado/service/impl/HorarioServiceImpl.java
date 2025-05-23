package dgtic.core.maquetado.service.impl;

import dgtic.core.maquetado.model.Cita;
import dgtic.core.maquetado.model.Horario;
import dgtic.core.maquetado.repository.HorarioRepository;
import dgtic.core.maquetado.service.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class HorarioServiceImpl extends GenericServiceImpl<Horario, Integer> implements HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    @Override
    JpaRepository<Horario, Integer> getRepository() {
        return horarioRepository;
    }

    @Override
    @Transactional
    public Horario update(Integer id, Horario entity) {
        return horarioRepository.findById(id)
                .map(entityExistente -> {
                    entityExistente.setHorarioConsultorio(entity.getHorarioConsultorio());
                    entityExistente.setFecha(entity.getFecha());
                    entityExistente.setHoraInicio(entity.getHoraInicio());
                    entityExistente.setHoraFin(entity.getHoraFin());

                    // --- Citas ---
                    Set<Cita> nuevasCitas = entity.getCitas();
                    entityExistente.getCitas().clear();
                    if (nuevasCitas != null) {
                        for (Cita c : nuevasCitas) {
                            c.setCitaHorario(entityExistente);
                            entityExistente.getCitas().add(c);
                        }
                    }
            return horarioRepository.save(entityExistente);
        }).orElseThrow(() -> new RuntimeException("Horario no encontrado con id: " + id));
    }
}
