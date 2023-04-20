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
import com.google.gson.Gson;
import java.util.HashMap;

@WebServlet("/getFields")

public class PdfGetAcroFields extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String pdfFile = "/export/wps/generatedLetters/summons.pdf";
        PdfReader reader = new PdfReader(pdfFile);
        Gson gson = new Gson();
        AcroFields fields = reader.getAcroFields();

        // Create a Map to store the key-value pairs
        Map<String, String> dataMap = new HashMap<>();
       // dataMap = fields.getFields();


        // Get the form fields in the PDF file
        Map<String,AcroFields.Item> fieldNames = fields.getFields();
      //  List<String> fieldNames = fields.getFields();

        // Print the field names to the console
        String returnString = "";
        for (String fieldName : fieldNames.keySet()) {
            returnString += (fieldName);
            dataMap.put(fieldName, "");
        }
        String json = gson.toJson(dataMap);
        PrintWriter out = response.getWriter();
        //turn this into an html form
        out.println(json);
        out.close();
    }
}
