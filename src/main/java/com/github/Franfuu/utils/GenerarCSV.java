package com.github.Franfuu.utils;

import com.github.Franfuu.model.dao.RecomendacionDAO;
import com.github.Franfuu.model.entities.Huella;
import com.github.Franfuu.model.entities.Recomendacion;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GenerarCSV {

    private static final RecomendacionDAO recomendacionDAO = new RecomendacionDAO();

    // Método para generar un archivo CSV con una lista de huellas
    public static void generateCSV(List<Huella> huellaList, Stage stage) {
        // Configura el selector de archivos para guardar el archivo CSV
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                // Agrega la cabecera al archivo CSV con los nombres de las columnas: Fecha, Valor, Actividad y Recomendaciones
                writer.append("Fecha,Valor,Actividad,Recomendaciones\n");

                // Itera sobre la lista de huellas para escribir cada una en el archivo CSV
                for (Huella huella : huellaList) {
                    // Obtiene las recomendaciones asociadas a la huella
                    List<Recomendacion> recomendaciones = recomendacionDAO.findByHuellaId(huella.getId());
                    // Convierte las descripciones de las recomendaciones en una cadena de texto separada por saltos de línea
                    String recomendacionesText = recomendaciones.stream()
                            .map(Recomendacion::getDescripcion)
                            .collect(Collectors.joining("\n"));

                    // Escribe los datos de la huella y sus recomendaciones en el archivo CSV
                    writer.append(huella.getFechaAsLocalDate().toString())
                            .append(',')
                            .append(huella.getValor().toString())
                            .append(',')
                            .append(huella.getIdActividad().getNombre())
                            .append(',')
                            .append(recomendacionesText)
                            .append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}