package com.github.Franfuu.view;

import com.github.Franfuu.model.dao.ActividadDAO;
import com.github.Franfuu.model.dao.HabitoDAO;
import com.github.Franfuu.model.entities.Actividad;
import com.github.Franfuu.model.entities.Habito;
import com.github.Franfuu.model.entities.HabitoId;
import com.github.Franfuu.utils.UsuarioSesion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class AgregarHabitoController extends Controller {
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

    private void saveHabito() {
        Actividad actividad = actividadComboBox.getValue();
        String frecuenciaText = frecuenciaField.getText();
        Integer frecuencia;
        try {
            frecuencia = Integer.parseInt(frecuenciaText);
        } catch (NumberFormatException e) {
            System.out.println("Invalid frequency: " + frecuenciaText);
            return;
        }
        LocalDate ultimaFecha = ultimaFechaPicker.getValue();
        Instant ultimaFechaInstant = ultimaFecha.atStartOfDay(ZoneId.systemDefault()).toInstant();
        String tipo = tipoField.getText();

        Habito habito = new Habito();
        habito.setId(new HabitoId(UsuarioSesion.getInstance().getUserLogged().getId(), actividad.getId()));
        habito.setIdUsuario(UsuarioSesion.getInstance().getUserLogged());
        habito.setIdActividad(actividad);
        habito.setFrecuencia(frecuencia);
        habito.setUltimaFecha(ultimaFechaInstant);
        habito.setTipo(tipo);

        HabitoDAO habitoDAO = new HabitoDAO();
        habitoDAO.insert(habito);

        // Close the modal
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}