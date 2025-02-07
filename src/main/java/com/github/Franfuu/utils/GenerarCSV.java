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

    public static void generateCSV(List<Huella> huellaList, Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.append("Fecha,Valor,Actividad,Recomendaciones\n");

                for (Huella huella : huellaList) {
                    List<Recomendacion> recomendaciones = recomendacionDAO.findByHuellaId(huella.getId());
                    String recomendacionesText = recomendaciones.stream()
                            .map(Recomendacion::getDescripcion)
                            .collect(Collectors.joining("\n"));

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