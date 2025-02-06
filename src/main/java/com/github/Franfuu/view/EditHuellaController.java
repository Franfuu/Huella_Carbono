package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.dao.ActividadDAO;
import com.github.Franfuu.model.dao.HuellaDAO;
import com.github.Franfuu.model.entities.Actividad;
import com.github.Franfuu.model.entities.Huella;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public class EditHuellaController extends Controller {

    @FXML
    private TextField valorField;
    @FXML
    private TextField unidadField;
    @FXML
    private ComboBox<Actividad> actividadComboBox;
    @FXML
    private DatePicker fechaPicker;
    @FXML
    private Button updateButton;

    private Huella huella;

    @Override
    public void onOpen(Object input) throws Exception {
        actividadComboBox.getItems().addAll(loadActividades());
    }

    @Override
    public void onClose(Object output) {
    }

    @FXML
    private void initialize() {
        actividadComboBox.setItems(FXCollections.observableArrayList(loadActividades()));
        updateButton.setOnAction(event -> saveHuella());
        if (huella != null) {
            setHuella(huella);
        }
    }

    public void setHuella(Huella huella) {
        this.huella = huella;
        actividadComboBox.setValue(huella.getIdActividad());
        valorField.setText(huella.getValor().toString());
        unidadField.setText(huella.getUnidad());
        fechaPicker.setValue(LocalDate.ofInstant(huella.getFecha(), ZoneId.systemDefault()));
    }
    @FXML
    private void saveHuella() {
        huella.setIdActividad(actividadComboBox.getValue());
        huella.setValor(new BigDecimal(valorField.getText()));
        huella.setUnidad(unidadField.getText());
        huella.setFecha(fechaPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        HuellaDAO huellaDAO = new HuellaDAO();
        huellaDAO.update(huella);
        try {
            App.currentController.changeScene(Scenes.MAINPAGE, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private List<Actividad> loadActividades() {
        ActividadDAO actividadDAO = new ActividadDAO();
        return actividadDAO.findAll();
    }
}