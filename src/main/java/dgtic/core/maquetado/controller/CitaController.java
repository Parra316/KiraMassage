package dgtic.core.maquetado.controller;

import dgtic.core.maquetado.model.*;
import dgtic.core.maquetado.security.model.UserDetailsImpl;
import dgtic.core.maquetado.service.CitaService;
import dgtic.core.maquetado.service.HorarioService;
import dgtic.core.maquetado.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class CitaController {

    @Autowired
    HorarioService horarioService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    CitaService citaService;

    @PostMapping("create-agendaCita")
    public String crearAgendaCita(@Valid Cita cita,
                                  @Valid Horario horario,
                            BindingResult bindingResult) {
        System.out.println("valor del id de horario: "+horario.getIdHorario());
        if(bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println("Error: " + error.getDefaultMessage());
            }
            return "redirect:/agendaCitas";
        }
        if (horario.getIdHorario() == null) {
            horario.setHoraFin(horario.getHoraInicio().plusMinutes(cita.getCitaServicio().getDuracion()));
            horarioService.create(horario);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Usuario user = userDetails.getUsuario();
        cita.setCitaUsuario(user);
        cita.setCitaConsultorio(horario .getHorarioConsultorio());
        cita.setFechaCita(horario.getFecha());
        cita.setCitaHorario(horario);
        cita.setEstatus("programado");
        citaService.create(cita);
        return "redirect:/agendaCitas";
    }

    @PostMapping("create-cita")
    public String crearCita(@Valid Cita cita,
                                   BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println("Error: " + error.getDefaultMessage());
            }
            return "redirect:/citas";
        }
        citaService.create(cita);
        return "redirect:/citas";
    }

    @PutMapping("update-cita/{id}")
    public String updateCita(@PathVariable Integer id,
                                    @ModelAttribute Cita cita) {
        citaService.update(id, cita);
        return "redirect:/consultorio";
    }

    @DeleteMapping("delete-cita/{id}")
    public String deleteCita(@PathVariable Integer id) {
        citaService.delete(id);
        return "redirect:/consultorio";
    }
}
