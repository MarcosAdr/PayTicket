package com.tesis.payticket.models.service;

import com.tesis.payticket.models.entity.Boleto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

@Service
public class PdfService {

    private static final String PDF_RESOURCES_PATH = "/pdf-resources/";


    private SpringTemplateEngine springTemplateEngine;


    private IBoletoService boletoService;

    @Autowired
    public PdfService(SpringTemplateEngine springTemplateEngine, IBoletoService boletoService) {
        this.springTemplateEngine = springTemplateEngine;
        this.boletoService = boletoService;
    }



    public File generateBoletoPdf(Long id) throws Exception {
        Boleto boleto = boletoService.findOne(id); // Obtener el boleto correspondiente al ID

        Context context = getContextBoletoPdf(boleto);
        String html = loadAndFillTemplate(context);
        String xhtml = convertToXhtml(html);
        return renderBoletoPdf(xhtml);
    }


    private String convertToXhtml(String html) throws UnsupportedEncodingException {
        Tidy tidy = new Tidy();
        tidy.setXHTML(true);
        tidy.setIndentContent(true);
        tidy.setPrintBodyOnly(true);
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setSmartIndent(true);
        tidy.setShowWarnings(false);
        tidy.setQuiet(true);
        tidy.setTidyMark(false);

        Document htmlDOM = tidy.parseDOM(new ByteArrayInputStream(html.getBytes()), null);

        OutputStream out = new ByteArrayOutputStream();
        tidy.pprint(htmlDOM, out);
        return out.toString();
    }

    private File renderBoletoPdf(String html) throws Exception {
        File file = File.createTempFile("boleto", ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 20);
        renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES_PATH).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();
        return file;
    }


    private Context getContextBoletoPdf(Boleto boleto) {
        Context context = new Context();
        context.setVariable("boleto", boleto);
        return context;
    }


    private String loadAndFillTemplate(Context context) {
        return springTemplateEngine.process("compras/ticket", context); // Cambia el nombre de la plantilla HTML según tus necesidades
    }


}
