package com.github.Franfuu.view;

import com.github.Franfuu.model.entities.Huella;
import com.github.Franfuu.services.HuellaService;
import com.github.Franfuu.model.connection.UsuarioSesion;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private final HuellaService huellaService = new HuellaService();

    @FXML
    private void initialize() {
        calcularButton.setOnAction(event -> calcularImpactoCarbono());
    }

    private void calcularImpactoCarbono() {
        LocalDate fechaInicio = fechaInicioPicker.getValue();
        LocalDate fechaFin = fechaFinPicker.getValue();

        if (fechaInicio == null || fechaFin == null) {
            showAlert(Alert.AlertType.ERROR, "Error de Fecha", "Por favor, seleccione ambas fechas.");
            return;
        }
        if (fechaInicio.isAfter(fechaFin)) {
            showAlert(Alert.AlertType.ERROR, "Error de Fecha", "La fecha de inicio no puede ser después de la fecha final, por favor, vuelva a rellenar.");
            return;
        }

        Instant inicio = fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant fin = fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant();

        List<Huella> huellas = huellaService.findByUsuarioAndFechaBetween(UsuarioSesion.getInstance().getUserLogged(), inicio, fin);

        Map<String, BigDecimal> impactoPorCategoria = huellas.stream()
                .collect(Collectors.groupingBy(
                        huella -> huella.getIdActividad().getIdCategoria().getNombre(),
                        Collectors.reducing(BigDecimal.ZERO,
                                huella -> huella.getValor().multiply(huella.getIdActividad().getIdCategoria().getFactorEmision()),
                                BigDecimal::add)
                ));

        BigDecimal impactoTotal = impactoPorCategoria.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        resultadoLabel.setText("Impacto de Carbono: " + impactoTotal + " kgCO2");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}