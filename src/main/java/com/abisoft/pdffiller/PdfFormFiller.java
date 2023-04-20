package com.abisoft.pdffiller;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.IOException;
import java.util.Map;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

public class PdfFormFiller {
    public static byte[] fillPdfForm(InputStream pdfTemplateStream, Map<String, String> formData) throws IOException, com.itextpdf.text.DocumentException {
        // Load the PDF file
        PdfReader reader = new PdfReader(pdfTemplateStream);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfStamper stamper = new PdfStamper(reader, baos);

        // Get the form fields
        AcroFields form = stamper.getAcroFields();

        // Set the form field values
        for (String fieldName : formData.keySet()) {
            form.setField(fieldName, formData.get(fieldName));
        }
        stamper.setFormFlattening(true);
        // Save the modified PDF file
        stamper.close();

        reader.close();
        return baos.toByteArray();
     }
}

