package dgtic.core.maquetado.service.impl;

import dgtic.core.maquetado.model.Cita;
import dgtic.core.maquetado.model.Usuario;
import dgtic.core.maquetado.repository.UsuarioRepository;
import dgtic.core.maquetado.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioServiceImpl extends GenericServiceImpl<Usuario, Integer> implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    JpaRepository<Usuario, Integer> getRepository() {
        return usuarioRepository;
    }

    public List<Usuario> findAllAdmin() {
        return usuarioRepository.findByRolNombre("ADMIN");
    }

    public List<Usuario> findAllClients() {
        return usuarioRepository.findByRolNombre("USER");
    }

    @Override
    public Optional<Usuario> findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    @Override
    @Transactional
    public Usuario update(Integer id, Usuario entity) {
        return usuarioRepository.findById(id)
                .map(entityExistente -> {
                    entityExistente.setNombre(entity.getNombre());
                    entityExistente.setApellidoMaterno(entity.getApellidoMaterno());
                    entityExistente.setApellidoPaterno(entity.getApellidoPaterno());
                    entityExistente.setEdad(entity.getEdad());
                    entityExistente.setCorreo(entity.getCorreo());
                    entityExistente.setContrasena(entity.getContrasena());
                    entityExistente.setTelefono(entity.getTelefono());

                    // --- Citas Usuarios---
                    Set<Cita> nuevasCitasUser = entity.getCitaUser();
                    entityExistente.getCitaUser().clear();
                    if (nuevasCitasUser != null) {
                        for (Cita c : nuevasCitasUser) {
                            c.setCitaUsuario(entityExistente);
                            entityExistente.getCitaUser().add(c);
                        }
                    }

                    // --- Citas Masajistas---
                    Set<Cita> nuevasCitasMas = entity.getCitaAdmin();
                    entityExistente.getCitaAdmin().clear();
                    if (nuevasCitasMas != null) {
                        for (Cita c : nuevasCitasMas) {
                            c.setCitaMasajista(entityExistente);
                            entityExistente.getCitaAdmin().add(c);
                        }
                    }

            return usuarioRepository.save(entityExistente);
        }).orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));
    }
}
