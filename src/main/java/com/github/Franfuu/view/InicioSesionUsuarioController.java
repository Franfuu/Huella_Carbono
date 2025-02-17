package com.github.Franfuu.view;

import com.github.Franfuu.services.UsuarioService;
import com.github.Franfuu.model.entities.Usuario;
import com.github.Franfuu.App;
import com.github.Franfuu.model.connection.UsuarioSesion;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.util.regex.Pattern;

public class InicioSesionUsuarioController extends Controller {

    private UsuarioService usuarioService = new UsuarioService();

    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> {
            try {
                login();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onOpen(Object input) throws Exception {
        // Implement your logic here
    }

    @Override
    public void onClose(Object output) {
    }

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;
    @FXML
    private Button loginButton;

    @FXML
    private void login() throws Exception {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.WARNING, "Advertencia", "Por favor, complete todos los campos.");
        } else if (!isValidEmail(email)) {
            showAlert(AlertType.WARNING, "Advertencia", "Por favor, introduzca un correo electrónico válido.");
        } else if (password.length() < 8) {
            showAlert(AlertType.WARNING, "Advertencia", "La contraseña debe tener al menos 8 caracteres.");
        } else {
            boolean isValidUser = usuarioService.validateUser(email, password);

            if (isValidUser) {
                Usuario usuario = usuarioService.findByEmailAndPassword(email,password);
                UsuarioSesion.getInstance().setUserLogged(usuario);
                showAlert(AlertType.CONFIRMATION, "Sesión Iniciada", "La sesión se ha iniciado correctamente.");
                App.currentController.changeScene(Scenes.MAINPAGE, usuario);
            } else {
                showAlert(AlertType.ERROR, "Error", "Usuario no encontrado o contraseña incorrecta.");
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

    public void setWelcomeButton() throws Exception {
        App.currentController.changeScene(Scenes.WELCOME, null);
    }
}