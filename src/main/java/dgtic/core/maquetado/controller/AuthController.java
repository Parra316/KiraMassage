package dgtic.core.maquetado.controller;

import dgtic.core.maquetado.model.Consultorio;
import dgtic.core.maquetado.model.Disponibilidad;
import dgtic.core.maquetado.security.jwt.JwtUtils;
import dgtic.core.maquetado.security.model.UserDetailsImpl;
import dgtic.core.maquetado.service.DisponibilidadService;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class AuthController {

    @Autowired
    DisponibilidadService disponibilidadService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/auth/login")
    public String loginSubmit(LoginRequest loginRequest,
                              HttpSession session,
                              Model modelo) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getCorreo(),
                            loginRequest.getContrasena()
                    )
            );

            // Establecer autenticación en el contexto de Spring
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Obtener detalles del usuario y generar token
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String token = jwtUtils.generateJwt(userDetails.getUsername());

            // Guardar el token en sesión
            session.setAttribute("jwt", token);

            // Obtener los roles del usuario
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            boolean isAdmin = authorities.stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

            // Redirigir según el rol
            if (isAdmin) {
                return "redirect:/servicios";
            } else {
                List<Disponibilidad> disponibilidadList = disponibilidadService.findAll();
                // Extraemos sólo los consultorios únicos
                List<Consultorio> consultorios = disponibilidadList.stream()
                        .map(Disponibilidad::getDispoConsultorio)
                        .collect(Collectors.collectingAndThen(
                                Collectors.toMap(
                                        Consultorio::getIdConsultorio,
                                        Function.identity(),
                                        (c1, c2) -> c1   // ante duplicados, conservar el primero
                                ),
                                m -> new ArrayList<>(m.values())
                        ));
                modelo.addAttribute("dispo", disponibilidadList);
                modelo.addAttribute("consultorios", consultorios);
                modelo.addAttribute("page", "Kira Massage");
                return "inicio";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login?error=true";
        }
    }


    // DTO interno
    @Data
    public static class LoginRequest {
        private String correo;
        private String contrasena;
    }
}

