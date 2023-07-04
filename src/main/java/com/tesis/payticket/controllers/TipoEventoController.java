package com.tesis.payticket.controllers;


import com.tesis.payticket.models.entity.TipoEvento;
import com.tesis.payticket.models.service.ITipoEventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;


@Controller
@RequestMapping("/tipoEvento")
@SessionAttributes("tipoEvento")
public class TipoEventoController {

    @Autowired
    ITipoEventoService tipoEventoService;

    @RequestMapping(value = "/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Tipos de Eventos");
        model.addAttribute("tipoEventos", tipoEventoService.findAll());
        return "tipoEvento/listar";
    }

    @RequestMapping(value = "/form")
    public String crear(Map<String, Object> model) {
        TipoEvento tipoEvento = new TipoEvento();
        model.put("tipoEvento", tipoEvento);
        model.put("titulo", "Agregar tipo de evento");
        return "tipoEvento/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String guardar(@Valid TipoEvento tipoEvento, BindingResult result, Model model,
                          RedirectAttributes flash, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Agregar tipo de evento");
            return "tipoEvento/form";
        }
        String mensajeFlash = (tipoEvento.getId() != null) ? "Tipo de evento editado con éxito!" : "Tipo de evento creado con éxito";
        tipoEventoService.save(tipoEvento);
        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/tipoEvento/listar";

    }

    @RequestMapping(value = "/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

        TipoEvento tipoEvento = null;

        if (id > 0) {
            tipoEvento = tipoEventoService.findOne(id);
            if (tipoEvento == null) {
                flash.addFlashAttribute("error", "El ID del evento no existe en la BBDD!");
                return "redirect:/tipoEvento/listar";
            }
        } else {
            flash.addFlashAttribute("error", "El ID del evento no puede ser cero!");
            return "redirect:/tipoEvento/listar";
        }
        model.put("tipoEvento", tipoEvento);

        model.put("titulo", "Modificar información de " + tipoEvento.getNombre());
        return "tipoEvento/form";
    }



    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

        if (id > 0) {
            TipoEvento tipoEvento = tipoEventoService.findOne(id);

            tipoEventoService.delete(id);
            flash.addFlashAttribute("success", "Tipo de evento eliminado con éxito!");

        }
        return "redirect:/tipoEvento/listar";
    }
}
