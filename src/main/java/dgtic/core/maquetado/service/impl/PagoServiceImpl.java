package dgtic.core.maquetado.service.impl;

import dgtic.core.maquetado.model.Pago;
import dgtic.core.maquetado.repository.PagoRepository;
import dgtic.core.maquetado.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PagoServiceImpl extends GenericServiceImpl<Pago, Integer> implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Override
    JpaRepository<Pago, Integer> getRepository() {
        return pagoRepository;
    }

    @Override
    @Transactional
    public Pago update(Integer id, Pago entity) {
        return pagoRepository.findById(id).map(entityExistente -> {
            entityExistente.setPagoCita(entity.getPagoCita());
            entityExistente.setMonto(entity.getMonto());
            entityExistente.setMetodo(entity.getMetodo());
            entityExistente.setEstatus(entity.getEstatus());
            entityExistente.setFecha(entity.getFecha());
            return pagoRepository.save(entityExistente);
        }).orElseThrow(() -> new RuntimeException("Pago no encontrado con id: " + id));
    }
}
