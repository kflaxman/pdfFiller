package com.abisoft.pdffiller;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;

@WebServlet("/pdfFiller")
//curl -X POST -H "Content-Type: application/json" -d '{"name": "John", "age": 30}' https://example.com/api/users

public class PdfFormFillerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String pdfTemplateFile = "/export/wps/generatedLetters/summons.pdf";
        File pdf = new File(pdfTemplateFile);

        InputStream pdfTemplateStream = new FileInputStream(pdf);

        //   String firstName = request.getParameter("firstName");
        String jsonPayload = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

        // Parse the JSON payload and extract its properties
        JsonObject jsonObject = Json.createReader(new StringReader(jsonPayload)).readObject();

       // String lastName = jsonObject.getString("Plaintiff");

        /* String email = request.getParameter("email");*/
        String fileName ="";
        Map<String, String> formData = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            String value = jsonObject.get(key).toString().replaceAll("\"", ""); // Remove quotation marks around string values
            if (key.equals("fileName")) {
                fileName = value;
            }
            else {
                formData.put(key, value);
            }
        }

        try {
            byte[] filledPdf = PdfFormFiller.fillPdfForm(pdfTemplateStream, formData);
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=myFilledForm.pdf");

            // Write the filled PDF file to the response output stream
            response.getOutputStream().write(filledPdf);
            response.getOutputStream().flush();
            PrintWriter out = response.getWriter();
            out.println("<html><body><h1>completed</h1></body></html>");
            out.close();
        }
        /*catch (ServletException exc) {
            PrintWriter out = response.getWriter();
            out.println("<html><body><h1>error</h1></body></html>" + exc.getMessage());
            out.close();
        }*/
       /* catch (IOException exc) {
            PrintWriter out = response.getWriter();
            out.println("<html><body><h1>IOerror</h1></body></html>" + exc.getMessage());
            out.close();
        }
        catch (com.itextpdf.text.DocumentException exc) {
            PrintWriter out = response.getWriter();
            out.println("<html><body><h1>DocException</h1></body></html>" + exc.getMessage());
            out.close();
        }*/ catch (Exception exc) {
            PrintWriter out = response.getWriter();
            out.println("<html><body><h1>Exception " + exc.getMessage() + "</h1></body></html>");
            out.close();
        }
    }
}
