package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.dao.ActividadDAO;
import com.github.Franfuu.model.entities.Actividad;
import com.github.Franfuu.model.entities.Huella;
import com.github.Franfuu.services.HuellaService;
import com.github.Franfuu.utils.UsuarioSesion;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class AgregarHuellaController extends Controller {
    private final HuellaService huellaService = new HuellaService();

    @FXML
    private TextField valorField;
    @FXML
    private TextField unidadField;
    @FXML
    private ComboBox<Actividad> actividadComboBox;
    @FXML
    private DatePicker fechaPicker;
    @FXML
    private Button saveButton;

    @Override
    public void onOpen(Object input) throws Exception {
        actividadComboBox.getItems().addAll(loadActividades());
    }

    @Override
    public void onClose(Object output) {
    }

    @FXML
    private void initialize() {
        saveButton.setOnAction(event -> saveHuella());
    }


    @FXML
    private void saveHuella() {
        if (actividadComboBox.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor, seleccione una actividad.");
            return;
        }
        if (valorField.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor, ingrese un valor.");
            return;
        }
        if (unidadField.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor, ingrese una unidad.");
            return;
        }
        if (fechaPicker.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor, seleccione una fecha.");
            return;
        }
        if (fechaPicker.getValue().isAfter(LocalDate.now())) {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "La fecha no puede ser futura.");
            return;
        }

        Huella huella = new Huella();
        huella.setIdUsuario(UsuarioSesion.getInstance().getUserLogged());
        huella.setIdActividad(actividadComboBox.getValue());
        huella.setValor(new BigDecimal(valorField.getText()));
        huella.setUnidad(unidadField.getText());
        huella.setFecha(fechaPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        huellaService.saveHuella(huella);

        showAlert(Alert.AlertType.INFORMATION, "Éxito", "Huella agregada correctamente.");

        // Close the modal
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private List<Actividad> loadActividades() {
        ActividadDAO actividadDAO = new ActividadDAO();
        return actividadDAO.findAll();
    }
}