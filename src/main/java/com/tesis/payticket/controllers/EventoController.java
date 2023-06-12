package com.tesis.payticket.controllers;

import com.tesis.payticket.models.entity.Evento;
import com.tesis.payticket.models.entity.TipoEvento;
import com.tesis.payticket.models.entity.Ubicacion;
import com.tesis.payticket.models.service.IEventoService;
import com.tesis.payticket.models.service.ITipoEventoService;
import com.tesis.payticket.models.service.IUbicacionService;
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

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

@Controller
@RequestMapping("/evento")
@SessionAttributes("evento")
public class EventoController {
    @Autowired
    private IEventoService eventoService;

    @Autowired
    private ITipoEventoService tipoEventoService;

    @Autowired
    private IUbicacionService ubicacionService;

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
    public String editar(@PathVariable(value = "id") Long id,
                         Map<String, Object> model, RedirectAttributes flash
                        ) {

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
        Ubicacion ubicacion = evento.getUbicacion();

        model.put("ubicacion", ubicacion);
        model.put("evento", evento);
        model.put("tiposEvento", tiposEvento);
        // Obtener el valor de la dirección y pasarlo al modelo
        String direccion = evento.getUbicacion() != null ? evento.getUbicacion().getDireccion() : "";
        model.put("direccion", direccion);

        model.put("titulo", "Modificar información de " + evento.getNombre());
        return "evento/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST) // mayor precision place_id
    public String guardar(@Valid Evento evento, BindingResult result, Model model,
                          @RequestParam("file") MultipartFile media,
                          @RequestParam("direccion") String direccion,
                          RedirectAttributes flash,
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
        Ubicacion ubicacion = null;
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyC4MsF7JmHdM1s1G9nHHb_lLys2kiLIvCY")
                .build();
        GeocodingResult[] results = null;
        try {
            results = GeocodingApi.geocode(context, direccion).await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (results.length > 0) {
            String cityName = results[0].addressComponents[2].longName;
            String cityCountry = results[0].addressComponents[4].shortName;
            String placeName = results[0].addressComponents[0].longName;
            String formattedAddress = results[0].formattedAddress;
            double latitude = results[0].geometry.location.lat;
            double longitude = results[0].geometry.location.lng;

            Long ubicacionId = null;
            if (evento.getUbicacion() != null) {
                ubicacionId = evento.getUbicacion().getId();
            }
            if (ubicacionId != null && ubicacionId > 0) {
                // Recuperar la ubicación existente de la base de datos
                ubicacion = ubicacionService.findOne(ubicacionId);
                // Actualizar las propiedades de la ubicación
            } else {
                // Crear una nueva instancia de ubicación
                ubicacion = new Ubicacion();
            }
            ubicacion.setCiudad(cityName);
            ubicacion.setPais(cityCountry);
            ubicacion.setLugar(placeName);
            ubicacion.setDireccion(formattedAddress);
            ubicacion.setLatitud(latitude);
            ubicacion.setLongitud(longitude);
            ubicacion.setNombre(direccion);
        }


        evento.setUbicacion(ubicacion);

        ubicacionService.save(ubicacion);
        eventoService.save(evento);
        String mensajeFlash = (evento.getId() != null) ? "Evento editado con éxito!" : "Evento creado con exito";
        flash.addFlashAttribute("success", mensajeFlash);
        status.setComplete();
        return "redirect:/evento/ver/" + evento.getId();
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
