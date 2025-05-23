package dgtic.core.maquetado.controller;

import dgtic.core.maquetado.model.Servicio;
import dgtic.core.maquetado.service.ServicioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@Controller
public class ServicioController {

    @Autowired
    ServicioService servicioService;

    @PostMapping("create-servicio")
    public String crearServicio(@Valid Servicio servicio,
                               BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println("Error: " + error.getDefaultMessage());
            }
            return "principal/servicios";
        }
        servicioService.create(servicio);
        return "principal/servicios";
    }

    @PutMapping("update-servicio/{id}")
    public String updateServicio(@PathVariable Integer id,
                                    @ModelAttribute Servicio servicio) {
        servicioService.update(id, servicio);
        return "redirect:/servicios";
    }

    @DeleteMapping("delete-servicio/{id}")
    public String deleteServicio(@PathVariable Integer id) {
        servicioService.delete(id);
        return "redirect:/servicios";
    }
}
