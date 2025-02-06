package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.dao.HuellaDAO;
import com.github.Franfuu.model.dao.ActividadDAO;
import com.github.Franfuu.model.entities.Huella;
import com.github.Franfuu.model.entities.Actividad;
import com.github.Franfuu.utils.UsuarioSesion;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.mysql.cj.util.TimeUtil.DATE_FORMATTER;

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
    private TableColumn<Huella, LocalDate> fechaColumn;
    @FXML
    private TableColumn<Huella, Void> actionColumn;
    @FXML
    private Button addHuellaButton;
    @FXML
    private Button addHabitoButton;
    @FXML
    private Button calcularImpactoButton;
    @FXML
    private Button verHabitosButton;

    private ObservableList<Huella> huellaList;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
        fechaColumn.setCellValueFactory(cellData -> {
            Huella huella = cellData.getValue();
            return new SimpleObjectProperty<>(huella.getFechaAsLocalDate());
        });

        fechaColumn.setCellFactory(new Callback<TableColumn<Huella, LocalDate>, TableCell<Huella, LocalDate>>() {
            @Override
            public TableCell<Huella, LocalDate> call(TableColumn<Huella, LocalDate> column) {
                return new TableCell<Huella, LocalDate>() {
                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(DATE_FORMATTER.format(item));
                        }
                    }
                };
            }
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

        calcularImpactoButton.setOnAction(event -> {
            try {
                openImpactoCarbonoModal();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        verHabitosButton.setOnAction(event -> {
            try {
                mostrarHabitosUsuario();
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
                    private final Button deleteButton = new Button("Eliminar");
                    private final Button editButton = new Button("Editar");

                    {
                        deleteButton.setOnAction(event -> {
                            Huella huella = getTableView().getItems().get(getIndex());
                            deleteHuella(huella);
                        });

                        editButton.setOnAction(event -> {
                            Huella huella = getTableView().getItems().get(getIndex());
                            try {
                                editHuella(huella);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(deleteButton, editButton);
                            setGraphic(buttons);
                        }
                    }
                };
                return cell;
            }
        };
        actionColumn.setCellFactory(cellFactory);
    }

    private void editHuella(Huella huella) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/github/Franfuu/view/EditHuella.fxml"));
        Parent root = loader.load();

        EditHuellaController controller = loader.getController();
        controller.setHuella(huella);

        Stage stage = (Stage) huellaTable.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Editar Huella");
        stage.show();
    }

    @FXML
    private void openImpactoCarbonoModal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/github/Franfuu/view/ImpactoCarbonoModal.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Impacto de Carbono");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarHabitosUsuario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/github/Franfuu/view/HabitosUsuario.fxml"));
            Parent root = loader.load();

            HabitosUsuarioController controller = loader.getController();
            controller.onOpen(UsuarioSesion.getInstance().getUserLogged());

            Stage stage = new Stage();
            stage.setTitle("Hábitos del Usuario");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}