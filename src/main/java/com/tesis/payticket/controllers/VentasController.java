package com.tesis.payticket.controllers;

import com.tesis.payticket.models.entity.Compra;
import com.tesis.payticket.models.entity.Evento;
import com.tesis.payticket.models.service.ICompraService;
import com.tesis.payticket.models.service.IEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ventas")
public class VentasController {

    @Autowired
    ICompraService compraService;

    @Autowired
    IEventoService eventoService;

    @GetMapping("/ver")
    public String verVentas(Model model) {
        List<Evento> eventos =  eventoService.findAll();
        model.addAttribute("eventos", eventos);
        return "ventas/ver";
    }

    @GetMapping("/ver/{id}")
    public String obtenerDetallesEvento(@PathVariable("id") Long eventoId, Model model) {
        Evento evento = eventoService.findOne(eventoId);
        if (evento != null) {
            model.addAttribute("evento", evento);
        } else {
            // Manejar caso de evento no encontrado
            model.addAttribute("error", "Evento no encontrado");
        }

        // Obtener la lista de eventos y agregarla al modelo nuevamente
        List<Evento> eventos = eventoService.findAll();
        model.addAttribute("eventos", eventos);

        return "/ventas/ver"; // Nombre de la vista que muestra el formulario y los detalles del evento
    }


}
