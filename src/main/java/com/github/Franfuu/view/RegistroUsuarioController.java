package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.dao.UsuarioDAO;
import com.github.Franfuu.model.entities.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class RegistroUsuarioController extends Controller {
    @Override
    public void onOpen(Object input) throws Exception {
    }

    @Override
    public void onClose(Object output) {
    }

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button registerButton;
    @FXML
    private Button welcomeButton;

    @FXML
    private void initialize() {
        registerButton.setOnAction(event -> handleRegister());
    }

    private void handleRegister() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        LocalDate registrationDate = LocalDate.now();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.WARNING, "Warning", "Please fill in all fields.");
        } else {
            try {
                saveUserToDatabase(name, email, password, registrationDate);
                showAlert(AlertType.INFORMATION, "Success", "Registration successful.");
            } catch (SQLException e) {
                showAlert(AlertType.ERROR, "Error", "Registration failed: " + e.getMessage());
            }
        }
    }

    private void saveUserToDatabase(String nombre, String email, String contraseña, LocalDate registrationDate) throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setContraseña(contraseña);
        usuario.setFechaRegistro(registrationDate.atStartOfDay().toInstant(ZoneOffset.UTC));

        usuarioDAO.insert(usuario);
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setWelcomeButton() throws Exception {
        App.currentController.changeScene(Scenes.WELCOME, null);
    }
}