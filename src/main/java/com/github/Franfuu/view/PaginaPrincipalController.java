package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.dao.HuellaDAO;
import com.github.Franfuu.model.entities.Huella;
import com.github.Franfuu.utils.UsuarioSesion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.BigDecimalStringConverter;

import java.math.BigDecimal;
import java.util.List;

public class PaginaPrincipalController extends Controller {

    @FXML
    private TableView<Huella> huellaTable;
    @FXML
    private TableColumn<Huella, Integer> idColumn;
    @FXML
    private TableColumn<Huella, String> actividadColumn;
    @FXML
    private TableColumn<Huella, BigDecimal> valorColumn;
    @FXML
    private TableColumn<Huella, String> unidadColumn;
    @FXML
    private TableColumn<Huella, String> fechaColumn;
    @FXML
    private TableColumn<Huella, Void> actionColumn;
    @FXML
    private Button addHuellaButton;
    @FXML
    private Button addHabitoButton;

    private ObservableList<Huella> huellaList;

    @Override
    public void onOpen(Object input) throws Exception {
        loadHuellas();
    }

    @Override
    public void onClose(Object output) {
    }

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        actividadColumn.setCellValueFactory(new PropertyValueFactory<>("idActividad"));
        valorColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));
        unidadColumn.setCellValueFactory(new PropertyValueFactory<>("unidad"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        // Enable editing
        huellaTable.setEditable(true);
        valorColumn.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        valorColumn.setOnEditCommit(event -> {
            Huella huella = event.getRowValue();
            huella.setValor(event.getNewValue());
            updateHuella(huella);
        });

        unidadColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        unidadColumn.setOnEditCommit(event -> {
            Huella huella = event.getRowValue();
            huella.setUnidad(event.getNewValue());
            updateHuella(huella);
        });

        addHuellaButton.setOnAction(event -> {
            try {
                App.currentController.changeScene(Scenes.ADDHUELLA, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        addHabitoButton.setOnAction(event -> {
            try {
                App.currentController.changeScene(Scenes.ADDHABITO, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        addButtonToTable();
    }

    private void loadHuellas() {
        HuellaDAO huellaDAO = new HuellaDAO();
        List<Huella> huellas = huellaDAO.findAllByUsuario(UsuarioSesion.getInstance().getUserLogged());
        huellaList = FXCollections.observableArrayList(huellas);
        huellaTable.setItems(huellaList);
    }

    private void updateHuella(Huella huella) {
        HuellaDAO huellaDAO = new HuellaDAO();
        huellaDAO.update(huella);
    }

    private void deleteHuella(Huella huella) {
        HuellaDAO huellaDAO = new HuellaDAO();
        huellaDAO.delete(huella);
        huellaList.remove(huella);
    }

    private void addButtonToTable() {

        Callback<TableColumn<Huella, Void>, TableCell<Huella, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Huella, Void> call(final TableColumn<Huella, Void> param) {
                final TableCell<Huella, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("Eliminar");

                    {
                        btn.setOnAction(event -> {
                            Huella huella = getTableView().getItems().get(getIndex());
                            deleteHuella(huella);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        actionColumn.setCellFactory(cellFactory);

    }
}