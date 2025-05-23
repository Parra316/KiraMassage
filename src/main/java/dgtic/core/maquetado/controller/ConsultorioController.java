package dgtic.core.maquetado.controller;

import dgtic.core.maquetado.model.Consultorio;
import dgtic.core.maquetado.model.Horario;
import dgtic.core.maquetado.service.ConsultorioService;
import dgtic.core.maquetado.service.HorarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ConsultorioController {

    @Autowired
    ConsultorioService consultorioService;

    @Autowired
    HorarioService horarioService;

    @PostMapping("create-consultorio")
    public String crearConsultorio(@Valid Consultorio consultorio,
                               BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println("Error: " + error.getDefaultMessage());
            }
            return "redirect:/consultorio";
        }
        consultorioService.create(consultorio);
        return "redirect:/consultorio";
    }

    @PutMapping("update-consultorio/{id}")
    public String updateConsultorio(@PathVariable Integer id,
                                    @ModelAttribute Consultorio consultorio) {
        consultorioService.update(id, consultorio);
        return "redirect:/consultorio";
    }

    @DeleteMapping("delete-consultorio/{id}")
    public String deleteConsultorio(@PathVariable Integer id) {
        consultorioService.delete(id);
        return "redirect:/consultorio";
    }

    @PostMapping("crear-horario")
    public String crearUsuario(@Valid Horario horario,
                               BindingResult bindingResult,
                               Model model) {
        if(bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println("Error: " + error.getDefaultMessage());
            }
            return "principal/horario";
        }
        horarioService.create(horario);
        List<Consultorio> consultorios = consultorioService.findAll();
        model.addAttribute("consultorios", consultorios);
        model.addAttribute("page", "horarios");
        return "principal/horario";
    }
}
