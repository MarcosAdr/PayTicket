package com.tesis.payticket.controllers;

import com.tesis.payticket.models.entity.Evento;
import com.tesis.payticket.models.entity.TipoEvento;
import com.tesis.payticket.models.service.IEventoService;
import com.tesis.payticket.models.service.ITipoEventoService;
import com.tesis.payticket.models.service.IUploadFileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/evento")
@SessionAttributes("evento")
public class EventoController {
    @Autowired
    private IEventoService eventoService;

    @Autowired
    private ITipoEventoService tipoEventoService;

    @Autowired
    private IUploadFileService uploadFileService;


    @GetMapping(value = "/uploads/{filename:.+}")
    public ResponseEntity<Resource> verMedia(@PathVariable String filename) {

        Resource recurso = null;
        try {
            recurso = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }

    @GetMapping(value = "/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

        Evento evento = eventoService.findOne(id);
        if (evento == null) {
            flash.addFlashAttribute("error", "El evento no existe en la base de datos");
            return "redirect:/evento/listar";
        }

        model.put("evento", evento);
        model.put("tipoEvento", evento.getTipoEvento());
        model.put("titulo", "Información del evento: " + evento.getNombre());
        return "evento/ver";
    }

    @RequestMapping(value = "/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Mis eventos");
        model.addAttribute("eventos", eventoService.findAll());
        return "evento/listar";
    }

    @RequestMapping(value = "/form")
    public String crear(Map<String, Object> model) {
        Evento evento = new Evento();
        List<TipoEvento> tiposEvento = tipoEventoService.findAll();
        model.put("evento", evento);
        model.put("tiposEvento", tiposEvento);
        model.put("titulo", "Crear Evento");
        return "evento/form";
    }

    @RequestMapping(value = "/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

        Evento evento = null;
        List<TipoEvento> tiposEvento = tipoEventoService.findAll();
        if (id > 0) {
            evento = eventoService.findOne(id);
            if (evento == null) {
                flash.addFlashAttribute("error", "El ID del evento no existe en la BBDD!");
                return "redirect:/evento/listar";
            }
        } else {
            flash.addFlashAttribute("error", "El ID del evento no puede ser cero!");
            return "redirect:/evento/listar";
        }
        model.put("evento", evento);
        model.put("tiposEvento", tiposEvento);
        model.put("titulo", "Modificar información de " + evento.getNombre());
        return "evento/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String guardar(@Valid Evento evento, BindingResult result, Model model,
                          @RequestParam("file") MultipartFile media, RedirectAttributes flash,
                          SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Crear Evento");
            List<TipoEvento> tiposEvento = tipoEventoService.findAll();
            model.addAttribute("tiposEvento", tiposEvento);
            return "evento/form";
        }

        TipoEvento tipoEvento = tipoEventoService.findOne(evento.getTipoEvento().getId());
        evento.setTipoEvento(tipoEvento);
        if (!media.isEmpty()) {
            if (evento.getId() != null && evento.getId() > 0 && evento.getMedia() != null && evento.getMedia().length() > 0) {

                uploadFileService.delete(evento.getMedia());
            }
            String uniqueFilename = null;
            try {
                uniqueFilename = uploadFileService.copy(media);
            } catch (IOException e) {

                e.printStackTrace();
            }
            flash.addFlashAttribute("info", "El Post publicitario se cargo correctamente '" + uniqueFilename + "'");
            evento.setMedia(uniqueFilename);
        }
        String mensajeFlash = (evento.getId() != null) ? "Evento editado con éxito!" : "Evento creado con exito";
        eventoService.save(evento);
        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/evento/listar";
    }

    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

        if (id > 0) {
            Evento evento = eventoService.findOne(id);

            eventoService.delete(id);
            flash.addFlashAttribute("success", "Post publicitario eliminado con éxito!");

            if (uploadFileService.delete(evento.getMedia())) {
                flash.addFlashAttribute("info", "Post publicitario " + evento.getMedia() + " eliminado con exito!");
            }

        }
        return "redirect:/evento/listar";
    }

}
