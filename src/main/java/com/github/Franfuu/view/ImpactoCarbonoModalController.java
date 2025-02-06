package com.github.Franfuu.view;

import com.github.Franfuu.model.dao.HuellaDAO;
import com.github.Franfuu.model.entities.Huella;
import com.github.Franfuu.utils.UsuarioSesion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class ImpactoCarbonoModalController extends Controller {
    @Override
    public void onOpen(Object input) throws Exception {

    }

    @Override
    public void onClose(Object output) {

    }

    @FXML
    private DatePicker fechaInicioPicker;
    @FXML
    private DatePicker fechaFinPicker;
    @FXML
    private Button calcularButton;
    @FXML
    private Label resultadoLabel;

    @FXML
    private void initialize() {
        calcularButton.setOnAction(event -> calcularImpactoCarbono());
    }

    private void calcularImpactoCarbono() {
        LocalDate fechaInicio = fechaInicioPicker.getValue();
        LocalDate fechaFin = fechaFinPicker.getValue();

        if (fechaInicio == null || fechaFin == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Fecha");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione ambas fechas.");
            alert.showAndWait();
            return;
        }
        if (fechaInicio != null && fechaFin != null && fechaInicio.isAfter(fechaFin)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Fecha");
            alert.setHeaderText(null);
            alert.setContentText("La fecha de inicio no puede ser después de la fecha final, por favor, vuelvalo a rellenar.");
            alert.showAndWait();
            return;
        }



        Instant inicio = fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant fin = fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant();

        HuellaDAO huellaDAO = new HuellaDAO();
        List<Huella> huellas = huellaDAO.findByUsuarioAndFechaBetween(UsuarioSesion.getInstance().getUserLogged(), inicio, fin);

        BigDecimal impactoTotal = huellas.stream()
                .map(Huella::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        resultadoLabel.setText("Impacto de Carbono: " + impactoTotal.toString());
    }
}