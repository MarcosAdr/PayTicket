package com.tesis.payticket.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class QRCodeGenerator {
     public ByteArrayOutputStream generarCodigoQR(String content, int width, int height) {
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height,
                    java.util.Collections.singletonMap(EncodeHintType.MARGIN, 0));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);
            return outputStream;
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void guardarImagenQR(String nombreImagen, byte[] imagenBytes) {
        try (OutputStream outputStream = new FileOutputStream("src/main/resources/pdf-resources/images/ImageQR/" + nombreImagen)) {
            outputStream.write(imagenBytes);
        } catch (IOException e) {
              e.printStackTrace();
        }
    }



}


