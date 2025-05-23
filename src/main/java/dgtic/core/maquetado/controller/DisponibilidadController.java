package dgtic.core.maquetado.controller;

import dgtic.core.maquetado.model.Disponibilidad;
import dgtic.core.maquetado.service.DisponibilidadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@Controller
public class DisponibilidadController {

    @Autowired
    DisponibilidadService disponibilidadService;

    @PostMapping("create-dispo")
    public String crearDisponibilidad(@Valid Disponibilidad disponibilidad,
                            BindingResult bindingResult,
                            Model modelo) {
        if(bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println("Error: " + error.getDefaultMessage());
            }
            return "redirect:/disponibilidad";
        }
        disponibilidadService.create(disponibilidad);
        return "redirect:/disponibilidad";
    }

    @DeleteMapping("delete-dispo/{id}")
    public String deleteDisponibilidad(@PathVariable Integer id) {
        disponibilidadService.delete(id);
        return "redirect:/disponibilidad";
    }
}
