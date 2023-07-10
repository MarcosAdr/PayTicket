package com.tesis.payticket.controllers;

import com.tesis.payticket.models.entity.Boleto;
import com.tesis.payticket.models.entity.Compra;
import com.tesis.payticket.models.service.IBoletoService;
import com.tesis.payticket.models.service.ICompraService;
import com.tesis.payticket.models.service.IUploadFileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;


@Controller
public class BoletoController {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private ICompraService compraService;

    @Autowired
    private IBoletoService boletoService;

    @Autowired
    private IUploadFileService uploadFileService;


    @GetMapping(value = "/images/QRCode/{filename:.+}")
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


    @GetMapping("/boleto/{id}")
    public void generarBoletoPDF(@PathVariable("id")Long id, HttpServletResponse response) throws Exception {
        // Obtener los datos del boleto a partir del ID
        Compra compra = compraService.findOne(id);

        Boleto boleto = boletoService.findOne(compra.getBoleto().getId());

        // Crear el contexto de Thymeleaf y establecer las variables
        Context context = new Context();
        context.setVariable("boleto", boleto);

        // Procesar la plantilla Thymeleaf para generar el HTML
        String html = templateEngine.process("compras/ticket", context);

        // Generar el PDF a partir del HTML generado
        //ByteArrayOutputStream byoutputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        //renderer.createPDF(byoutputStream);

        // Establecer las cabeceras de la respuesta HTTP
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"boleto.pdf\"");

        // Escribir el PDF en la respuesta
        OutputStream outputStream = response.getOutputStream();
        //outputStream.write(byoutputStream.toByteArray());
        renderer.createPDF(outputStream);
        outputStream.close();
    }
}

