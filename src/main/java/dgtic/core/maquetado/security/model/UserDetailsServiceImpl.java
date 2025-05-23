package dgtic.core.maquetado.security.model;

import dgtic.core.maquetado.model.Usuario;
import dgtic.core.maquetado.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepo;

    @Autowired
    public UserDetailsServiceImpl(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String correo)
            throws UsernameNotFoundException {

        Usuario usuario = usuarioRepo
                .findByCorreoWithRoles(correo)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado: " + correo)
                );

        // aquí ya 'usuario.getRoles()' está completamente inicializado
        return new UserDetailsImpl(usuario);
    }
}

