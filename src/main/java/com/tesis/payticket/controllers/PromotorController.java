package com.tesis.payticket.controllers;

import com.tesis.payticket.models.entity.Promotor;
import com.tesis.payticket.models.entity.TipoEvento;
import com.tesis.payticket.models.service.IPromotorService;
import com.tesis.payticket.models.service.ITipoEventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PromotorController {


    @Autowired
    private ITipoEventoService tipoEventoService;

    @Autowired
    private IPromotorService promotorService;

    @GetMapping("/quienSomos")
    public String quienSomos() {
        return "descripcion";
    }

    @GetMapping("/contacto")
    public String contacto(Model model) {
        Promotor promotor = new Promotor();
        List<TipoEvento> tiposEvento = tipoEventoService.findAll();
        model.addAttribute("promotor", promotor);
        model.addAttribute("tiposEvento", tiposEvento);
        return "promotor/cotizacion";
    }

    @PostMapping("/contacto/form")
    public String guardarContacto(@Valid Promotor promotor, Model model,
                                  BindingResult result, RedirectAttributes flash,
                                    @RequestParam("tipoEvento") Long tipoEventoId
                                  ) {

        if (result.hasErrors()) {
            List<TipoEvento> tiposEvento = tipoEventoService.findAll();
            model.addAttribute("tiposEvento", tiposEvento);
            return "promotor/cotizacion";
        }

        TipoEvento tipoEvento = tipoEventoService.findOne(tipoEventoId);

        promotor.setTipoEvento(tipoEvento.getNombre());

        promotorService.save(promotor);

        flash.addFlashAttribute("success", "Nos pondremos en contacto pronto");
        return "redirect:/";
    }


}
