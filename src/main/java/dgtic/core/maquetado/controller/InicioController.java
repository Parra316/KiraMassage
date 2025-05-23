package dgtic.core.maquetado.controller;

import dgtic.core.maquetado.model.*;
import dgtic.core.maquetado.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class InicioController {

    @Autowired
    CitaService citaService;

    @Autowired
    ConsultorioService consultorioService;

    @Autowired
    DisponibilidadService disponibilidadService;

    @Autowired
    RolService rolService;

    @Autowired
    ServicioService servicioService;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping(value = "/")
    public String inicio(Model modelo){
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
        return  "inicio";
    }

    @GetMapping("agendaCitas")
    public String agendarCitas(
            @RequestParam(name = "consultorioId", required = false) Integer consultorioId,
            @RequestParam(name = "servicioId",    required = false) Integer servicioId,
            Model modelo) {

        // 1) Cargamos todas las Disponibilidades
        List<Disponibilidad> disponibilidadList = disponibilidadService.findAll();

        // 2) Extraemos solo los consultorios únicos
        List<Consultorio> consultorios = disponibilidadList.stream()
                .map(Disponibilidad::getDispoConsultorio)
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                Consultorio::getIdConsultorio,
                                Function.identity(),
                                (c1, c2) -> c1
                        ),
                        m -> new ArrayList<>(m.values())
                ));

        // 3) Construimos el mapa consultorioId -> List<Servicio>
        Map<Integer, List<Servicio>> serviciosPorConsultorio = disponibilidadList.stream()
                .collect(Collectors.groupingBy(
                        d -> d.getDispoConsultorio().getIdConsultorio(),
                        Collectors.mapping(Disponibilidad::getDispoServicio, Collectors.toList())
                ));

        // 4) Cargamos listas estáticas para usuarios y (todos) los servicios
        List<Usuario> clientes    = usuarioService.findAllClients();
        List<Usuario> masajistas  = usuarioService.findAllAdmin();
        List<Servicio> servicios  = servicioService.findAll();

        // 5) Llenamos el modelo
        modelo.addAttribute("fechaActual", LocalDate.now().plusDays(1));
        modelo.addAttribute("clientes",    clientes);
        modelo.addAttribute("masajistas",  masajistas);
        modelo.addAttribute("servicios",   servicios);
        modelo.addAttribute("consultorios",           consultorios);
        modelo.addAttribute("servicioSeleccionado",   servicioId);
        modelo.addAttribute("consultorioSeleccionado",consultorioId);
        // <-- Nuevo atributo para el filtrado dinámico -->
        modelo.addAttribute("serviciosPorConsultorio", serviciosPorConsultorio);
        modelo.addAttribute("page", "Agendar Cita");
        return "principal/agendaCitas";
    }

    @GetMapping(value = "citas")
    public String citas(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        Model modelo) {
        // 1) Cargamos todas las Disponibilidades
        List<Disponibilidad> disponibilidadList = disponibilidadService.findAll();

        // 2) Extraemos solo los consultorios únicos
        List<Consultorio> consultorios = disponibilidadList.stream()
                .map(Disponibilidad::getDispoConsultorio)
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                Consultorio::getIdConsultorio,
                                Function.identity(),
                                (c1, c2) -> c1
                        ),
                        m -> new ArrayList<>(m.values())
                ));

        // 3) Construimos el mapa consultorioId -> List<Servicio>
        Map<Integer, List<Servicio>> serviciosPorConsultorio = disponibilidadList.stream()
                .collect(Collectors.groupingBy(
                        d -> d.getDispoConsultorio().getIdConsultorio(),
                        Collectors.mapping(Disponibilidad::getDispoServicio, Collectors.toList())
                ));

        // 4) Cargamos listas estáticas para usuarios y (todos) los servicios
        List<Usuario> clientes    = usuarioService.findAllClients();
        List<Usuario> masajistas  = usuarioService.findAllAdmin();
        List<Servicio> servicios  = servicioService.findAll();

        // 5) Llenamos el modelo
        modelo.addAttribute("fechaActual", LocalDate.now().plusDays(1));
        modelo.addAttribute("clientes",    clientes);
        modelo.addAttribute("masajistas",  masajistas);
        modelo.addAttribute("servicios",   servicios);
        modelo.addAttribute("consultorios",           consultorios);
        modelo.addAttribute("serviciosPorConsultorio", serviciosPorConsultorio);
        Pageable pageable = PageRequest.of(page, size);
        Page<Cita> citaPage = citaService.findAll(pageable);
        modelo.addAttribute("citPage", citaPage);
        modelo.addAttribute("citas", citaPage.getContent());  // <-- aquí
        modelo.addAttribute("cita", new Cita());
        modelo.addAttribute("page", "Citas");
        return "principal/citas";
    }

    @GetMapping(value = "consultorio")
    public String consultorio(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              Model modelo) {
        modelo.addAttribute("consultorio", new Consultorio());
        Pageable pageable = PageRequest.of(page, size);
        Page<Consultorio> consultorioPage = consultorioService.findAll(pageable);
        modelo.addAttribute("con", consultorioPage);
        modelo.addAttribute("page", "Consultorios");
        return "principal/consultorio";
    }

    @GetMapping(value = "servicios")
    public String servicios(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model modelo){
        modelo.addAttribute("servicio", new Servicio());
        Pageable pageable = PageRequest.of(page, size);
        Page<Servicio> servicioPage = servicioService.findAll(pageable);
        modelo.addAttribute("ser", servicioPage);
        modelo.addAttribute("page", "Servicios");
        return "principal/servicios";
    }

    @GetMapping(value = "usuarios")
    public String usuarios(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model modelo){
        modelo.addAttribute("usuario", new Usuario());
        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> usuarioPage = usuarioService.findAll(pageable);
        modelo.addAttribute("user", usuarioPage);
        modelo.addAttribute("page", "Usuarios");
        return "principal/usuarios";
    }

    @GetMapping(value = "disponibilidad")
    public String disponibilidad(Model modelo){
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
        // lista de todos los servicios
        List<Servicio> servicios = servicioService.findAll();

        modelo.addAttribute("dispo", disponibilidadList);
        modelo.addAttribute("consultorios", consultorios);
        modelo.addAttribute("servicios", servicios);
        modelo.addAttribute("page", "Disponibilidad");
        return "principal/disponibilidad";
    }

    @GetMapping("/roles")
    public String usuarioRoles(Model model) {
        List<Rol> roles = rolService.findAll();        // trae cada Rol con su Set<UsuarioRol>
        List<Usuario> users = usuarioService.findAll(); // todos los usuarios

        model.addAttribute("page", "Roles");
        model.addAttribute("roles", roles);
        model.addAttribute("users", users);
        return "principal/roles";
    }


    @GetMapping(value = "pagos")
    public String pagos(Model modelo){
        modelo.addAttribute("page", "pagos");
        return "principal/pagos";
    }

    @GetMapping(value = "resenas")
    public String resenas(Model modelo){
        modelo.addAttribute("page", "resenas");
        return "principal/resenas";
    }

    @GetMapping(value = "login")
    public String login(Model modelo){
        modelo.addAttribute("usuario", new Usuario());
        modelo.addAttribute("page", "Iniciar Sesion");
        return "principal/login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        return "principal/logout";
    }

    @GetMapping("/login?logout=true")
    public String logoutSuccess(){
        return "principal/logoutSucess";
    }
}