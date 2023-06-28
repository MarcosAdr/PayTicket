package com.tesis.payticket.controllers;

import com.tesis.payticket.models.entity.Role;
import com.tesis.payticket.models.entity.Usuario;
import com.tesis.payticket.models.service.IRoleService;
import com.tesis.payticket.models.service.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping(value = "/register")
    public String registrar(Model model) {
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        return "auth/register";
    }

    @PostMapping(value = "/register")
    public String RegistrarUser(@Valid Usuario usuario, BindingResult result,
                                @RequestParam(name = "password") String password,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Registrar Usuario");
            return "auth/register";
        }
        Role rol = new Role();
        rol.setRol("ROLE_USER");

        roleService.save(rol);
        usuario.setRoles(Collections.singletonList(rol));

        String passwordBcrypt = passwordEncoder.encode(password);
        usuario.setEnabled(true);
        usuario.setPassword(passwordBcrypt);
        usuario.setUsername(usuario.getNombre());
        usuarioService.save(usuario);
        return "redirect:/login";
    }


}
