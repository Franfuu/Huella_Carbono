package com.github.Franfuu.view;

import com.github.Franfuu.model.dao.ActividadDAO;
import com.github.Franfuu.model.entities.Actividad;
import com.github.Franfuu.model.entities.Habito;
import com.github.Franfuu.model.entities.HabitoId;
import com.github.Franfuu.services.HabitoService;
import com.github.Franfuu.model.connection.UsuarioSesion;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class AgregarHabitoController extends Controller {
    private final HabitoService habitoService = new HabitoService();

    @Override
    public void onOpen(Object input) throws Exception {
    }

    @Override
    public void onClose(Object output) {
    }

    @FXML
    private ComboBox<Actividad> actividadComboBox;
    @FXML
    private TextField frecuenciaField;
    @FXML
    private DatePicker ultimaFechaPicker;
    @FXML
    private TextField tipoField;
    @FXML
    private Button saveButton;

    @FXML
    private void initialize() {
        loadActividades();
        saveButton.setOnAction(event -> saveHabito());
    }

    private void loadActividades() {
        ActividadDAO actividadDAO = new ActividadDAO();
        List<Actividad> actividades = actividadDAO.findAll();
        actividadComboBox.getItems().addAll(actividades);
    }

    @FXML
    private void saveHabito() {
        Actividad actividad = actividadComboBox.getValue();
        if (actividad == null) {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor, seleccione una actividad.");
            return;
        }

        String frecuenciaText = frecuenciaField.getText();
        Integer frecuencia;
        try {
            frecuencia = Integer.parseInt(frecuenciaText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "Frecuencia inválida: " + frecuenciaText);
            return;
        }

        LocalDate ultimaFecha = ultimaFechaPicker.getValue();
        if (ultimaFecha == null) {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "Por favor, seleccione una fecha.");
            return;
        }
        if (ultimaFecha.isAfter(LocalDate.now())) {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "La fecha no puede ser futura.");
            return;
        }
        Instant ultimaFechaInstant = ultimaFecha.atStartOfDay(ZoneId.systemDefault()).toInstant();
        String tipo = tipoField.getText();

        Habito habito = new Habito();
        habito.setId(new HabitoId(UsuarioSesion.getInstance().getUserLogged().getId(), actividad.getId()));
        habito.setIdUsuario(UsuarioSesion.getInstance().getUserLogged());
        habito.setIdActividad(actividad);
        habito.setFrecuencia(frecuencia);
        habito.setUltimaFecha(ultimaFechaInstant);
        habito.setTipo(tipo);

        habitoService.saveHabito(habito);

        showAlert(Alert.AlertType.INFORMATION, "Éxito", "Hábito agregado correctamente.");

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
}