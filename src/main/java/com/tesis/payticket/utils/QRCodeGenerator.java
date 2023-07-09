package com.tesis.payticket.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.tesis.payticket.models.entity.Compra;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QRCodeGenerator {

  /*  public static void generateQRCode(Compra compra) throws WriterException, IOException {

        String qrCodePath = "/QRCode";

        String qrCodeName = compra.getIdTransaccion() + compra.getLocalidad() +
                compra.getUsuario().getCompras() + "-QRCODE.png";

        var qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode("ID: " + compra.getId()
                        + "Localidad: " + compra.getLocalidad().getNombre()
                        + "Evento: " + compra.getLocalidad().getEvento().getNombre()
                        + "Fecha: " + compra.getLocalidad().getEvento().getFechaEvento()
                        + "FechaTransaccion: " + compra.getFechaTransaccion(),
                BarcodeFormat.QR_CODE, 350, 350);
        Path path = FileSystems.getDefault().getPath(qrCodeName);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }*/

    public ByteArrayOutputStream generarCodigoQR(String content, int width, int height) {
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height,
                    java.util.Collections.singletonMap(EncodeHintType.MARGIN, 0));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);
            return outputStream;
        } catch (WriterException | IOException e) {
            // Manejar el error de generación del código QR
            e.printStackTrace();
            return null;
        }
    }

    public void guardarImagenQR(String nombreImagen, byte[] imagenBytes) {
        try (OutputStream outputStream = new FileOutputStream("src/main/resources/static/images/QRCode/" + nombreImagen)) {
            outputStream.write(imagenBytes);
        } catch (IOException e) {
            // Manejar el error de escritura de la imagen
            e.printStackTrace();
        }
    }



}


