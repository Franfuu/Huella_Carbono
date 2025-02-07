package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.entities.Huella;
import com.github.Franfuu.model.entities.Recomendacion;
import com.github.Franfuu.services.HabitoService;
import com.github.Franfuu.services.HuellaService;
import com.github.Franfuu.utils.GenerarCSV;
import com.github.Franfuu.utils.GenerarPDF;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    @FXML
    private Button editUserButton;
    @FXML
    private TableColumn<Huella, Void> recomendacionColumn;
    @FXML
    private Button generateDocumentationButton;

    private ObservableList<Huella> huellaList;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final HuellaService huellaService = new HuellaService();
    private final HabitoService habitoService = new HabitoService();

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
                openAgregarHuellaModal();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        addHabitoButton.setOnAction(event -> {
            try {
                openAgregarHabitoModal();
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
        editUserButton.setOnAction(event -> {
            try {
                openEditUserModal();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        addButtonToTable();
        addRecomendacionButtonToTable();
        generateDocumentationButton.setOnAction(event -> generateDocumentation());
    }

    private void addRecomendacionButtonToTable() {
        Callback<TableColumn<Huella, Void>, TableCell<Huella, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Huella, Void> call(final TableColumn<Huella, Void> param) {
                final TableCell<Huella, Void> cell = new TableCell<>() {
                    private final Button recomendacionButton = new Button("Recomendaciones");

                    {
                        recomendacionButton.setOnAction(event -> {
                            Huella huella = getTableView().getItems().get(getIndex());
                            try {
                                mostrarRecomendaciones(huella);
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
                            setGraphic(recomendacionButton);
                        }
                    }
                };
                return cell;
            }
        };
        recomendacionColumn.setCellFactory(cellFactory);
    }

    @FXML
    private void generateDocumentation() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Generar Reporte");
        alert.setHeaderText("Seleccione el formato del reporte");

        ButtonType pdfButton = new ButtonType("Generar PDF");
        ButtonType csvButton = new ButtonType("Generar CSV");
        ButtonType cancelButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(pdfButton, csvButton, cancelButton);

        alert.showAndWait().ifPresent(type -> {
            if (type == pdfButton) {
                huellaService.generatePDF(huellaList, (Stage) generateDocumentationButton.getScene().getWindow());
            } else if (type == csvButton) {
                huellaService.generateCSV(huellaList, (Stage) generateDocumentationButton.getScene().getWindow());
            }
        });
    }

    private void mostrarRecomendaciones(Huella huella) throws Exception {
        List<Recomendacion> recomendaciones = habitoService.getRecomendacionesPorHuella(huella);

        if (recomendaciones == null || recomendaciones.isEmpty()) {
            recomendaciones = Collections.emptyList();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Recomendaciones");
        alert.setHeaderText("Recomendaciones para la huella ID: " + huella.getId().toString());
        alert.setContentText(recomendaciones.stream()
                .map(Recomendacion::getDescripcion)
                .collect(Collectors.joining("\n")));
        alert.showAndWait();
    }

    private void loadHuellas() {
        List<Huella> huellas = huellaService.findAllByUsuario(UsuarioSesion.getInstance().getUserLogged());
        huellaList = FXCollections.observableArrayList(huellas);
        huellaTable.setItems(huellaList);
    }

    private void deleteHuella(Huella huella) {
        huellaService.deleteHuella(huella);
        huellaList.remove(huella);
        showAlert(Alert.AlertType.INFORMATION, "Éxito", "Huella eliminada correctamente.");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openEditUserModal() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/github/Franfuu/view/EditUser.fxml"));
        Parent root = loader.load();

        EditUserController controller = loader.getController();
        controller.setUser(UsuarioSesion.getInstance().getUserLogged());

        Stage stage = new Stage();
        stage.setTitle("Edit User");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();

        refreshMainPage();
    }
    private void openAgregarHabitoModal() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/github/Franfuu/view/AgregarHabito.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Agregar Hábito");
        stage.setScene(new Scene(root));
        stage.showAndWait();

        refreshMainPage();
    }

    private void openAgregarHuellaModal() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/github/Franfuu/view/AgregarHuella.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Agregar Huella");
        stage.setScene(new Scene(root));
        stage.showAndWait();

        refreshMainPage();
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

        Stage stage = new Stage();
        stage.setTitle("Edit Huella");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();

        // Refresh the main page after closing the modal
        refreshMainPage();
    }

    private void refreshMainPage() {
        try {
            App.currentController.changeScene(Scenes.MAINPAGE, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
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