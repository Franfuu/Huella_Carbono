package com.github.Franfuu.view;

import com.github.Franfuu.model.dao.HabitoDAO;
import com.github.Franfuu.model.entities.Habito;
import com.github.Franfuu.model.entities.Recomendacion;
import com.github.Franfuu.model.entities.Usuario;
import com.github.Franfuu.services.HabitoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class HabitosUsuarioController extends Controller {
    @FXML
    private TableView<Habito> habitosTableView;
    @FXML
    private TableColumn<Habito, String> actividadColumn;
    @FXML
    private TableColumn<Habito, Integer> frecuenciaColumn;
    @FXML
    private TableColumn<Habito, String> tipoColumn;
    @FXML
    private TableColumn<Habito, Instant> ultimaFechaColumn;
    @FXML
    private TableColumn<Habito, Void> deleteColumn;

    private ObservableList<Habito> habitosList;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @Override
    public void onOpen(Object input) throws Exception {
        Usuario usuario = (Usuario) input;
        loadHabitos(usuario);
    }

    @Override
    public void onClose(Object output) {
    }

    private void loadHabitos(Usuario usuario) {
        HabitoDAO habitoDAO = new HabitoDAO();
        List<Habito> habitos = habitoDAO.findAllByUsuario(usuario);
        habitosList = FXCollections.observableArrayList(habitos);

        actividadColumn.setCellValueFactory(new PropertyValueFactory<>("idActividad"));
        frecuenciaColumn.setCellValueFactory(new PropertyValueFactory<>("frecuencia"));
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        ultimaFechaColumn.setCellValueFactory(new PropertyValueFactory<>("ultimaFecha"));
        ultimaFechaColumn.setCellFactory(new Callback<TableColumn<Habito, Instant>, TableCell<Habito, Instant>>() {
            @Override
            public TableCell<Habito, Instant> call(TableColumn<Habito, Instant> column) {
                return new TableCell<Habito, Instant>() {
                    @Override
                    protected void updateItem(Instant item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            LocalDate localDate = item.atZone(ZoneId.systemDefault()).toLocalDate();
                            setText(DATE_FORMATTER.format(localDate));
                        }
                    }
                };
            }
        });
        deleteColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Habito, Void> call(final TableColumn<Habito, Void> param) {
                final TableCell<Habito, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("Eliminar");

                    {
                        btn.setOnAction(event -> {
                            Habito habito = getTableView().getItems().get(getIndex());
                            deleteHabito(habito);
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
        });

        habitosTableView.setItems(habitosList);
    }

    private void deleteHabito(Habito habito) {
        HabitoDAO habitoDAO = new HabitoDAO();
        habitoDAO.delete(habito);
        habitosList.remove(habito);
    }
}