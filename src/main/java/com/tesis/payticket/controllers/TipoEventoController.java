package com.tesis.payticket.controllers;

import com.tesis.payticket.models.service.ITipoEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/tipoEvento")
public class TipoEventoController {

    @Autowired
    ITipoEventoService tipoEventoService;


    @RequestMapping(value = "/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Tipos de Eventos");
        model.addAttribute("tipoEventos", tipoEventoService.findAll());
        return "tipoEvento/listar";
    }
}
