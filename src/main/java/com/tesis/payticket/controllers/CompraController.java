package com.tesis.payticket.controllers;

import com.tesis.payticket.models.entity.Boleto;
import com.tesis.payticket.models.entity.Compra;
import com.tesis.payticket.models.service.IBoletoService;
import com.tesis.payticket.models.service.ICompraService;
import com.tesis.payticket.models.service.IUsuarioService;
import com.tesis.payticket.utils.QRCodeGenerator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring6.SpringTemplateEngine;


import java.io.*;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/compras")
@Slf4j
public class CompraController {

    @Autowired
    private ICompraService compraService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IBoletoService boletoService;


    private QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();

    @GetMapping("/ver")
    public String misCompras(Model model, Authentication auth) {

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();

        Long userId = usuarioService.findByUsername(username).getId();

        List<Compra> compras = compraService.findByUsuarioId(userId);
        model.addAttribute("compras", compras);
        return "compras/ver";
    }

    @GetMapping("/generar/{compraId}")
    public void generarBoleto(@PathVariable("compraId") String compraIdStr, Model model,
                                HttpServletResponse response
    ) {
        Long compraId = Long.parseLong(compraIdStr);

        Compra compra = compraService.findOne(compraId);

            String qrContent = "©PayTicket " + LocalDate.now() + "\n" +
                    "ID: " + compra.getTransaccion() + "\n" +
                    "Fecha: " + compra.getFechaTransaccion() + "\n" +
                    "Valor: $" + compra.getMonto() + "\n" +
                    "Cantidad de boletos: " + compra.getCantidad() + "\n" +
                    "Localidad: " + compra.getLocalidad().getNombre() + "\n" +
                    "Evento: " + compra.getLocalidad().getEvento().getNombre() + "\n" +
                    "Usuario: " + compra.getUsuario().getUsername() + " " + compra.getUsuario().getApellido();

            ByteArrayOutputStream qrOutputStream = qrCodeGenerator.generarCodigoQR(qrContent, 200, 200);

            String qrImageName = "qr_" + compra.getTransaccion() + compra.getId() + compra.getLocalidad().getId() +
                    compra.getLocalidad().getEvento().getId() + compra.getCantidad() + ".png";

            qrCodeGenerator.guardarImagenQR(qrImageName, qrOutputStream.toByteArray());

            Boleto boleto = new Boleto();
            boleto.setCodigoQR(qrImageName);
            boletoService.save(boleto);
            compra.setBoleto(boleto);

            compraService.save(compra);

            }

}

