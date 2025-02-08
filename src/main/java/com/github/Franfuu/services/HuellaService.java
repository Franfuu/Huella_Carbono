package com.github.Franfuu.services;

import com.github.Franfuu.model.dao.HuellaDAO;
import com.github.Franfuu.model.dao.RecomendacionDAO;
import com.github.Franfuu.model.entities.Huella;
import com.github.Franfuu.model.entities.Usuario;
import com.github.Franfuu.utils.GenerarCSV;
import com.github.Franfuu.utils.GenerarPDF;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HuellaService {

    private final HuellaDAO huellaDAO;
    private final RecomendacionDAO recomendacionDAO;

    public HuellaService() {
        this.huellaDAO = new HuellaDAO();
        this.recomendacionDAO = new RecomendacionDAO();
    }

    // Guarda una huella en la base de datos, validando que la fecha no sea futura
    public void saveHuella(Huella huella) {
        if (huella.getFecha().isAfter(Instant.now())) {
            throw new IllegalArgumentException("La fecha no puede ser futura.");
        }
        huellaDAO.insert(huella);
    }

    // Actualiza una huella en la base de datos, validando que la fecha no sea futura
    public void updateHuella(Huella huella) {
        if (huella.getFecha().isAfter(Instant.now())) {
            throw new IllegalArgumentException("La fecha no puede ser futura.");
        }
        huellaDAO.update(huella);
    }

    // Elimina una huella de la base de datos
    public void deleteHuella(Huella huella) {
        huellaDAO.delete(huella);
    }

    // Encuentra todas las huellas de un usuario
    public List<Huella> findAllByUsuario(Usuario usuario) {
        return huellaDAO.findAllByUsuario(usuario);
    }

    // Genera un PDF con la lista de huellas
    public void generatePDF(List<Huella> huellaList, Stage stage) {
        GenerarPDF.generatePDF(huellaList, stage);
    }

    // Genera un CSV con la lista de huellas
    public void generateCSV(List<Huella> huellaList, Stage stage) {
        GenerarCSV.generateCSV(huellaList, stage);
    }

    // Encuentra huellas de un usuario en un rango de fechas
    public List<Huella> findByUsuarioAndFechaBetween(Usuario usuario, Instant inicio, Instant fin) {
        return huellaDAO.findByUsuarioAndFechaBetween(usuario, inicio, fin);
    }

    // Obtiene la huella de carbono del usuario agrupada por categoría
    public Map<String, BigDecimal> getUserCarbonFootprintByCategory(Usuario usuario) {
        List<Object[]> results = huellaDAO.findHuellaWithActividadAndCategoria(usuario);
        return results.stream()
                .collect(Collectors.groupingBy(
                        result -> (String) result[2], // Nombre de la categoría
                        Collectors.reducing(BigDecimal.ZERO,
                                result -> ((BigDecimal) result[0]).multiply((BigDecimal) result[1]), // Valor de la huella * Factor de emisión
                                BigDecimal::add)
                ));
    }
}