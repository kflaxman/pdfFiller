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
import com.google.gson.JsonObject;

@WebServlet("/getFields")

public class PdfGetAcroFields extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String pdfFile = request.getParameter("fileName");
        PrintWriter out = response.getWriter();
        PdfReader reader = new PdfReader(pdfFile);
        Gson gson = new Gson();
        AcroFields fields = reader.getAcroFields();

        // Create a Map to store the key-value pairs
        Map<String, String> dataMap = new HashMap<>();
        // Get the form fields in the PDF file
        Map<String,AcroFields.Item> fieldNames = fields.getFields();
        JsonObject innerObj = new JsonObject();
        for (String fieldName : fieldNames.keySet()) {
           // dataMap.put(fieldName, "");
            innerObj.addProperty(fieldName, "");
        }
        JsonObject outerObj = new JsonObject();
        outerObj.addProperty("fileName", pdfFile);
        outerObj.add("fields", innerObj);

        //   String json = gson.toJson(dataMap);

        out.println(gson.toJson(outerObj));
        out.close();
    }
}
