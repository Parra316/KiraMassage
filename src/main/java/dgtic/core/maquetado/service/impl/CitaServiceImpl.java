package dgtic.core.maquetado.service.impl;

import dgtic.core.maquetado.model.Cita;
import dgtic.core.maquetado.model.Horario;
import dgtic.core.maquetado.model.Pago;
import dgtic.core.maquetado.repository.CitaRepository;
import dgtic.core.maquetado.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class CitaServiceImpl extends GenericServiceImpl<Cita, Integer> implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Override
    JpaRepository<Cita, Integer> getRepository() {
        return citaRepository;
    }

    @Override
    @Transactional
    public Cita update(Integer id, Cita entity) {
        return citaRepository.findById(id)
                .map(entityExistente -> {
                    // Actualiza campos sencillos
                    entityExistente.setCitaUsuario(entity.getCitaUsuario());
                    entityExistente.setCitaMasajista(entity.getCitaMasajista());
                    entityExistente.setCitaServicio(entity.getCitaServicio());
                    entityExistente.setCitaConsultorio(entity.getCitaConsultorio());
                    entityExistente.setCitaHorario(entity.getCitaHorario());
                    entityExistente.setFechaCita(entity.getFechaCita());
                    entityExistente.setEstatus(entity.getEstatus());

                    // --- Pagos ---
                    Set<Pago> nuevosPagos = entity.getPagos();
                    entityExistente.getPagos().clear();
                    if (nuevosPagos != null) {
                        for (Pago p : nuevosPagos) {
                            p.setPagoCita(entityExistente);
                            entityExistente.getPagos().add(p);
                        }
                    }
            return citaRepository.save(entityExistente);
        }).orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));
    }
}
