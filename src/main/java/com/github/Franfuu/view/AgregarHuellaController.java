package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.dao.ActividadDAO;
import com.github.Franfuu.model.dao.HuellaDAO;
import com.github.Franfuu.model.entities.Actividad;
import com.github.Franfuu.model.entities.Huella;
import com.github.Franfuu.utils.UsuarioSesion;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public class AgregarHuellaController extends Controller {

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
        Huella huella = new Huella();
        huella.setIdUsuario(UsuarioSesion.getInstance().getUserLogged());
        huella.setIdActividad(actividadComboBox.getValue());
        huella.setValor(new BigDecimal(valorField.getText()));
        huella.setUnidad(unidadField.getText());
        huella.setFecha(fechaPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        HuellaDAO huellaDAO = new HuellaDAO();
        huellaDAO.insert(huella);
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