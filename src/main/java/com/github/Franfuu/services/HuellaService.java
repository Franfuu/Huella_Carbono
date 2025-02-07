package com.github.Franfuu.services;

import com.github.Franfuu.model.dao.HuellaDAO;
import com.github.Franfuu.model.dao.RecomendacionDAO;
import com.github.Franfuu.model.entities.Huella;
import com.github.Franfuu.model.entities.Recomendacion;
import com.github.Franfuu.model.entities.Usuario;
import com.github.Franfuu.utils.GenerarCSV;
import com.github.Franfuu.utils.GenerarPDF;
import com.github.Franfuu.utils.UsuarioSesion;
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

    public void saveHuella(Huella huella) {
        if (huella.getFecha().isAfter(Instant.now())) {
            throw new IllegalArgumentException("La fecha no puede ser futura.");
        }
        huellaDAO.insert(huella);
    }

    public void updateHuella(Huella huella) {
        if (huella.getFecha().isAfter(Instant.now())) {
            throw new IllegalArgumentException("La fecha no puede ser futura.");
        }
        huellaDAO.update(huella);
    }

    public void deleteHuella(Huella huella) {
        huellaDAO.delete(huella);
    }

    public List<Huella> findAllByUsuario(Usuario usuario) {
        return huellaDAO.findAllByUsuario(usuario);
    }

    public void generatePDF(List<Huella> huellaList, Stage stage) {
        GenerarPDF.generatePDF(huellaList, stage);
    }

    public void generateCSV(List<Huella> huellaList, Stage stage) {
        GenerarCSV.generateCSV(huellaList, stage);
    }

    public List<Huella> findByUsuarioAndFechaBetween(Usuario usuario, Instant inicio, Instant fin) {
        return huellaDAO.findByUsuarioAndFechaBetween(usuario, inicio, fin);
    }

    public Map<String, BigDecimal> getAverageCarbonFootprintByCategory() {
        List<Huella> huellas = huellaDAO.findAllByUsuario(UsuarioSesion.getInstance().getUserLogged());
        return huellas.stream()
                .collect(Collectors.groupingBy(
                        huella -> huella.getIdActividad().getIdCategoria().getNombre(),
                        Collectors.averagingDouble(huella -> huella.getValor().doubleValue())
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> BigDecimal.valueOf(entry.getValue())));
    }

    public Map<String, BigDecimal> getUserCarbonFootprintByCategory(Usuario usuario) {
        List<Huella> huellas = huellaDAO.findAllByUsuario(usuario);
        return huellas.stream()
                .collect(Collectors.groupingBy(
                        huella -> huella.getIdActividad().getIdCategoria().getNombre(),
                        Collectors.reducing(BigDecimal.ZERO, Huella::getValor, BigDecimal::add)
                ));
    }

}