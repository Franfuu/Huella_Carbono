package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.entities.Usuario;
import com.github.Franfuu.services.UsuarioService;
import com.github.Franfuu.utils.HashearContraseña;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.regex.Pattern;

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

    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    private void initialize() {
        registerButton.setOnAction(event -> register());
    }

    private void register() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        LocalDate registrationDate = LocalDate.now();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.WARNING, "Advertencia", "Por favor, complete todos los campos.");
        } else if (!isValidEmail(email)) {
            showAlert(AlertType.WARNING, "Advertencia", "Por favor, introduzca un correo electrónico válido.");
        } else if (password.length() < 8) {
            showAlert(AlertType.WARNING, "Advertencia", "La contraseña debe tener al menos 8 caracteres.");
        } else {
            try {
                saveUserToDatabase(name, email, password, registrationDate);
                showAlert(AlertType.INFORMATION, "Éxito", "Registro exitoso.");
            } catch (SQLException e) {
                showAlert(AlertType.ERROR, "Error", "El registro falló: " + e.getMessage());
            }
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(emailRegex, email);
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void saveUserToDatabase(String nombre, String email, String contraseña, LocalDate registrationDate) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setContraseña(contraseña);
        usuario.setFechaRegistro(registrationDate.atStartOfDay().toInstant(ZoneOffset.UTC));

        usuarioService.saveUser(usuario);
    }

    public void setWelcomeButton() throws Exception {
        App.currentController.changeScene(Scenes.WELCOME, null);
    }
}