package com.tesis.payticket.controllers;

import com.tesis.payticket.models.entity.Evento;
import com.tesis.payticket.models.entity.Localidad;
import com.tesis.payticket.models.service.IEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/localidad")
public class LocalidadController {

    @Autowired
    private IEventoService eventoService;

    @GetMapping("/form/{eventoId}")
    public String addLocalidad(@PathVariable(value = "eventoId") Long eventoId,
                               Map<String, Object> model, RedirectAttributes flash){
        Evento evento = eventoService.findOne(eventoId);
        if(evento == null){
            flash.addFlashAttribute("error",
                    "El evento no existe en la base de datos");
            return "redirect:/evento/listar";
        }

        Localidad localidad = new Localidad();
        localidad.setEvento(evento);

        model.put("localidad", localidad);
        model.put("titulo", "Añadir categoria o localidad a " +evento.getNombre());

        return "localidad/form";
    }
}
