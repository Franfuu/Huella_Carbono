package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.dao.ActividadDAO;
import com.github.Franfuu.model.dao.HuellaDAO;
import com.github.Franfuu.model.entities.Actividad;
import com.github.Franfuu.model.entities.Huella;
import com.github.Franfuu.utils.UsuarioSesion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class AgregarHuellaController extends Controller {

    @FXML
    private TextField valorField;
    @FXML
    private TextField unidadField;
    @FXML
    private ComboBox<Actividad> actividadComboBox;
    @FXML
    private Button saveButton;

    @Override
    public void onOpen(Object input) throws Exception {
        // Load activities into the ComboBox
        actividadComboBox.getItems().addAll(loadActividades());
    }

    @Override
    public void onClose(Object output) {
    }

    @FXML
    private void initialize() {
        saveButton.setOnAction(event -> saveHuella());
    }

    private void saveHuella() {
        BigDecimal valor = new BigDecimal(valorField.getText());
        String unidad = unidadField.getText();
        Actividad actividad = actividadComboBox.getValue();

        Huella huella = new Huella();
        huella.setValor(valor);
        huella.setUnidad(unidad);
        huella.setIdActividad(actividad);
        huella.setIdUsuario(UsuarioSesion.getInstance().getUserLogged());
        huella.setFecha(Instant.now());

        HuellaDAO huellaDAO = new HuellaDAO();
        huellaDAO.insert(huella);

        // Close the current scene or navigate back to the main page
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