package com.abisoft.pdffiller;
import javax.servlet.ServletException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
@WebServlet("/getFields")

public class PdfGetAcroFields extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String pdfFile = "/export/wps/generatedLetters/summons.pdf";
        PdfReader reader = new PdfReader(pdfFile);

        // Get the form fields in the PDF file
        AcroFields fields = reader.getAcroFields();
        Map<String,AcroFields.Item> fieldNames = fields.getFields();
      //  List<String> fieldNames = fields.getFields();

        // Print the field names to the console
        String returnString = "";
        for (String fieldName : fieldNames.keySet()) {
            returnString += (fieldName);
        }
        PrintWriter out = response.getWriter();
        //turn this into an html form
        out.println("<html><body><h1>" + returnString + "</h1></body></html>");
        out.close();
    }
}
