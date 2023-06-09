package com.tesis.payticket.controllers;

import com.tesis.payticket.models.entity.Evento;
import com.tesis.payticket.models.entity.Localidad;
import com.tesis.payticket.models.entity.Precio;
import com.tesis.payticket.models.service.IEventoService;
import com.tesis.payticket.models.service.ILocalidadService;
import com.tesis.payticket.models.service.IPrecioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/localidad")
@SessionAttributes("localidad")
public class LocalidadController {

    @Autowired
    private IEventoService eventoService;

    @Autowired
    private ILocalidadService localidadService;

    @Autowired
    private IPrecioService precioService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/form/{eventoId}")
    public String crear(@PathVariable(value = "eventoId") Long eventoId,
                        Map<String, Object> model, RedirectAttributes flash) {
        Evento evento = eventoService.findOne(eventoId);
        if (evento == null) {
            flash.addFlashAttribute("error",
                    "El evento no existe en la base de datos");
            return "redirect:/evento/listar";
        }

        Localidad localidad = new Localidad();
        localidad.setEvento(evento);

        model.put("localidad", localidad);
        model.put("titulo", "Añadir Localidad");

        return "localidad/form";
    }

    @PostMapping("/form") //validar precio
    public String guardar(@Valid Localidad localidad,
                          BindingResult localidadResult,
                          @Valid Precio precio,
                          BindingResult precioResult,/*no hace nd */
                          @RequestParam("entradas") int entradas,
                          RedirectAttributes flash, SessionStatus status,
                          Map<String, Object> model) {


        if (localidadResult.hasErrors() || precioResult.hasErrors()) {
            model.put("titulo", "Añadir Localidad");
            return "localidad/form";
        }

        Evento evento = eventoService.findOne(localidad.getEvento().getId());
        int entradasDispo = evento.getTotalEntradas() - entradas;

        if (entradas <= 0 || entradas > evento.getTotalEntradas()) {
            model.put("titulo", "Añadir Localidad");
            model.put("errorEntradas", "Ingrese un valor válido de entradas");
            return "localidad/form";
        }

        evento.setTotalEntradas(entradasDispo);

        precio = localidad.getPrecio();

        precioService.save(precio);

        localidadService.save(localidad);
        eventoService.save(evento);

        status.setComplete();
        flash.addFlashAttribute("success", "Localidad guardada");
        return "redirect:/evento/ver/" + localidad.getEvento().getId();
    }

    @RequestMapping(value = "/form/{idEvento}/{idLocalidad}")
    public String editar(@PathVariable(value = "idEvento") Long idEvento,
                         @PathVariable(value = "idLocalidad") Long idLocalidad,
                         Map<String, Object> model) {

        Evento evento = eventoService.findOne(idEvento);
        Localidad localidad = localidadService.findOne(idLocalidad);
        int entradasDispo = evento.getTotalEntradas()+localidad.getEntradas();
        evento.setTotalEntradas(entradasDispo);
        eventoService.save(evento);
        model.put("evento",evento);
        model.put("localidad",localidad);
        return "localidad/form";

    }

    @RequestMapping(value = "/eliminar/{idEvento}/{idLocalidad}")
    public String eliminar(@PathVariable(value = "idEvento") Long idEvento,
                           @PathVariable(value = "idLocalidad") Long idLocalidad,
                           RedirectAttributes flash){
        Evento evento = eventoService.findOne(idEvento);
        Localidad localidad = localidadService.findOne(idLocalidad);
        int entradasDispo = evento.getTotalEntradas()+localidad.getEntradas();
        evento.setTotalEntradas(entradasDispo);
        eventoService.save(evento);

        localidadService.delete(idLocalidad);
        flash.addFlashAttribute("success", "Localidad eliminada");
        return "redirect:/evento/ver/" + evento.getId();

    }





}
