package com.github.Franfuu.utils;

import com.github.Franfuu.model.dao.RecomendacionDAO;
import com.github.Franfuu.model.entities.Huella;
import com.github.Franfuu.model.entities.Recomendacion;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GenerarPDF {

    private static final RecomendacionDAO recomendacionDAO = new RecomendacionDAO();

    public static void generatePDF(List<Huella> huellaList, Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();
                document.add(new Paragraph("Historial de Huellas de Carbono" + "\n\n"));

                PdfPTable table = new PdfPTable(4);
                table.addCell("Fecha");
                table.addCell("Valor");
                table.addCell("Actividad");
                table.addCell("Recomendaciones");

                for (Huella huella : huellaList) {
                    List<Recomendacion> recomendaciones = recomendacionDAO.findByHuellaId(huella.getId());
                    String recomendacionesText = recomendaciones.stream()
                            .map(Recomendacion::getDescripcion)
                            .collect(Collectors.joining("\n"));

                    table.addCell(huella.getFechaAsLocalDate().toString());
                    table.addCell(huella.getValor().toString());
                    table.addCell(huella.getIdActividad().getNombre());
                    table.addCell(recomendacionesText);
                }

                document.add(table);
                document.close();
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}