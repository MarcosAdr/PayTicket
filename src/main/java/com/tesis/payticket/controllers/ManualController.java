package com.tesis.payticket.controllers;

import com.tesis.payticket.models.service.IEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManualController {

   @GetMapping(value = {"/manual"})
    public String index(Model model) {
        return "manual/manual";
    }



}
