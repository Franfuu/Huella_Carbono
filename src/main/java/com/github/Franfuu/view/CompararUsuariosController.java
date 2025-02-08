package com.github.Franfuu.view;

import com.github.Franfuu.model.entities.Usuario;
import com.github.Franfuu.services.HuellaService;
import com.github.Franfuu.services.UsuarioService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

    public class CompararUsuariosController extends Controller {

        @FXML
        private TableView<Usuario> usuariosTable;
        @FXML
        private TableColumn<Usuario, String> nombreColumn;
        @FXML
        private PieChart usuarioPieChart;
        @FXML
        private PieChart comparadoPieChart;
        @FXML
        private Label usuarioLogueadoLabel;
        @FXML
        private Label comparacionLabel;

        private final UsuarioService usuarioService = new UsuarioService();
        private final HuellaService huellaService = new HuellaService();
        private Usuario usuarioLogueado;

        public void setUsuarioLogueado(Usuario usuario) {
            this.usuarioLogueado = usuario;
            cargarUsuarios();
            mostrarHuellaUsuarioLogueado();

        }

        private void cargarUsuarios() {
            List<Usuario> usuarios = usuarioService.findAll().stream()
                    .filter(usuario -> !usuario.equals(usuarioLogueado))
                    .collect(Collectors.toList());
            ObservableList<Usuario> usuariosObservableList = FXCollections.observableArrayList(usuarios);
            usuariosTable.setItems(usuariosObservableList);
        }

        private void mostrarHuellaUsuarioLogueado() {
            Map<String, BigDecimal> huellaUsuarioLogueado = huellaService.getUserCarbonFootprintByCategory(usuarioLogueado);
            usuarioPieChart.setData(FXCollections.observableArrayList(
                    huellaUsuarioLogueado.entrySet().stream()
                            .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue().doubleValue()))
                            .collect(Collectors.toList())
            ));
            BigDecimal impactoTotal = huellaUsuarioLogueado.values().stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            usuarioLogueadoLabel.setText("Impacto de Carbono del Usuario Logueado: " + impactoTotal + " kgCO2");
        }

        @FXML
        private void initialize() {
            nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            usuariosTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    compararUsuarios(newSelection);
                }
            });
        }


        private void compararUsuarios(Usuario usuarioComparado) {
            Map<String, BigDecimal> huellaUsuarioComparado = huellaService.getUserCarbonFootprintByCategory(usuarioComparado);

            comparadoPieChart.setData(FXCollections.observableArrayList(
                    huellaUsuarioComparado.entrySet().stream()
                            .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue().doubleValue()))
                            .collect(Collectors.toList())
            ));

            BigDecimal impactoTotalComparado = huellaUsuarioComparado.values().stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            comparacionLabel.setText("Impacto de Carbono del Usuario Comparado (" + usuarioComparado.getNombre() + "): " + impactoTotalComparado + " kgCO2");
        }

        @Override
        public void onOpen(Object input) throws Exception {

        }

        @Override
        public void onClose(Object output) {

        }
    }