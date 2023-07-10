package com.tesis.payticket.controllers;

import com.tesis.payticket.models.entity.Boleto;
import com.tesis.payticket.models.entity.Compra;
import com.tesis.payticket.models.service.IBoletoService;
import com.tesis.payticket.models.service.ICompraService;
import com.tesis.payticket.models.service.PdfService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
public class BoletoController {

    @Autowired
    private ICompraService compraService;

    @Autowired
    private IBoletoService boletoService;


    @Autowired
    private PdfService pdfService;


    @GetMapping("/boleto/{id}")
    public void downloadPdf(@PathVariable("id") Long id, HttpServletResponse response) {
        Compra compra = compraService.findOne(id);
        Boleto boleto = boletoService.findOne(compra.getBoleto().getId());

        try {
            Path file = Paths.get(pdfService.generateBoletoPdf(boleto.getId()).getAbsolutePath());
            if (Files.exists(file)) {
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition", "attachment; filename=" + file.getFileName());
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}

