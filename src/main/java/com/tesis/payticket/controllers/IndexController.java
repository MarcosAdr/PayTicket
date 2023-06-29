package com.tesis.payticket.controllers;

import com.tesis.payticket.models.service.IEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    private IEventoService eventoService;

    @GetMapping(value = {"/","/index"})
    public String index(Model model) {
        model.addAttribute("eventos", eventoService.findAll());
        return "index";
    }


}
