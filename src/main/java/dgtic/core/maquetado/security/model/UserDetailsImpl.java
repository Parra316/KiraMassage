package dgtic.core.maquetado.security.model;

import dgtic.core.maquetado.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private final Usuario usuario;

    public UserDetailsImpl(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getNombre() {
        return usuario.getNombre();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return usuario.getUsuarioRoles().stream()
                .map(ur -> new SimpleGrantedAuthority(
                        "ROLE_" + ur.getRol().getNombre().toUpperCase()
                ))
                .collect(Collectors.toSet());
    }


    @Override
    public String getPassword() {
        return usuario.getContrasena();
    }

    @Override
    public String getUsername() {
        return usuario.getCorreo();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // puedes controlar esto con un campo extra si lo deseas
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // igual
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // igual
    }

    @Override
    public boolean isEnabled() {
        return true; // puedes tener un campo "activo" en Usuario
    }
}
