package com.tesis.payticket.controllers;

import com.tesis.payticket.models.entity.Compra;
import com.tesis.payticket.models.service.ICompraService;
import com.tesis.payticket.models.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private ICompraService compraService;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/ver")
    public String misCompras(Model model, Authentication auth) {

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();

        Long userId = usuarioService.findByUsername(username).getId();

        List<Compra> compras = compraService.findByUsuarioId(userId);
        model.addAttribute("compras", compras);
        return "compras/ver";


    }

}
