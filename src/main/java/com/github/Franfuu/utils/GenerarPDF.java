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

    // Instancia de RecomendacionDAO para acceder a las recomendaciones asociadas a las huellas
    private static final RecomendacionDAO recomendacionDAO = new RecomendacionDAO();

    // Método estático para generar un archivo PDF con una lista de huellas
    public static void generatePDF(List<Huella> huellaList, Stage stage) {
        // Configura el selector de archivos para guardar el archivo PDF
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            // Crea un nuevo documento PDF
            Document document = new Document();
            try {
                // Inicializa el escritor de PDF con el archivo seleccionado
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();
                // Agrega un título al documento
                document.add(new Paragraph("Historial de Huellas de Carbono" + "\n\n"));

                // Crea una tabla con 4 columnas para mostrar los datos de las huellas
                PdfPTable table = new PdfPTable(4);
                table.addCell("Fecha");
                table.addCell("Valor");
                table.addCell("Actividad");
                table.addCell("Recomendaciones");

                // Itera sobre la lista de huellas para agregar cada una a la tabla
                for (Huella huella : huellaList) {
                    // Obtiene las recomendaciones asociadas a la huella
                    List<Recomendacion> recomendaciones = recomendacionDAO.findByHuellaId(huella.getId());
                    // Convierte las descripciones de las recomendaciones en una cadena de texto separada por saltos de línea
                    String recomendacionesText = recomendaciones.stream()
                            .map(Recomendacion::getDescripcion)
                            .collect(Collectors.joining("\n"));

                    // Agrega los datos de la huella y sus recomendaciones a la tabla
                    table.addCell(huella.getFechaAsLocalDate().toString());
                    table.addCell(huella.getValor().toString());
                    table.addCell(huella.getIdActividad().getNombre());
                    table.addCell(recomendacionesText);
                }

                // Agrega la tabla al documento y cierra el documento
                document.add(table);
                document.close();
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}