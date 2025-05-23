package dgtic.core.maquetado.controller;

import dgtic.core.maquetado.model.Rol;
import dgtic.core.maquetado.model.Usuario;
import dgtic.core.maquetado.model.UsuarioRol;
import dgtic.core.maquetado.service.RolService;
import dgtic.core.maquetado.service.UsuarioRolService;
import dgtic.core.maquetado.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class UsuarioController {

    @Autowired
    RolService rolService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRolService usuarioRolService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("create-usuario")
    public String crearUsuario(@Valid Usuario usuario,
                               BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println("Error: " + error.getDefaultMessage());
            }
            return "redirect:/usuarios";
        }
        String encriptada = passwordEncoder.encode(usuario.getContrasena());
        usuario.setContrasena(encriptada);
        usuario.setRegistro(LocalDateTime.now());
        usuarioService.create(usuario);
        return "redirect:/usuarios";
    }

    @PostMapping("create-usuarioLogin")
    public String crearUsuarioLogin(@Valid Usuario usuario,
                               BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println("Error: " + error.getDefaultMessage());
            }
            return "redirect:/login";
        }
        Rol r = rolService.getById(2);
        String encriptada = passwordEncoder.encode(usuario.getContrasena());
        usuario.setContrasena(encriptada);
        usuario.setRegistro(LocalDateTime.now());
        usuario.addRol(r);
        usuarioService.create(usuario);
        return "redirect:/login";
    }

    @PutMapping("update-usuario/{id}")
    public String updateUsuario(@PathVariable Integer id,
                                 @ModelAttribute Usuario usuario) {
        String encriptada = passwordEncoder.encode(usuario.getContrasena());
        usuario.setContrasena(encriptada);
        usuario.setRegistro(LocalDateTime.now());
        usuarioService.update(id, usuario);
        return "redirect:/usuarios";
    }

    @DeleteMapping("delete-usuario/{id}")
    public String deleteUsuario(@PathVariable Integer id) {
        usuarioService.delete(id);
        return "redirect:/usuarios";
    }

    @PostMapping("/update-rol")
    public String updateRol(@RequestParam("roleId") Integer roleId,
                            @RequestParam("userId") Integer userId) {
        int tmpRol=0;
        Rol rol = rolService.getById(roleId);
        Usuario usuario = usuarioService.getById(userId);
        if(roleId == 2){
            tmpRol =1;
        }else if(roleId == 1){
            tmpRol =2;
        }
        UsuarioRol ur = new UsuarioRol();
        ur.setUser(usuario);
        ur.setRol(rol);
        usuarioRolService.deleteUsuarioRol(userId, tmpRol);
        usuarioRolService.save(ur);

        return "redirect:/roles";
    }
}
