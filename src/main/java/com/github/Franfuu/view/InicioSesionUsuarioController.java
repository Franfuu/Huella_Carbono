package com.github.Franfuu.view;

import com.github.Franfuu.services.UsuarioService;
import com.github.Franfuu.model.entities.Usuario;
import com.github.Franfuu.App;
import com.github.Franfuu.utils.UsuarioSesion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class InicioSesionUsuarioController extends Controller {

    private UsuarioService usuarioService = new UsuarioService();

    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> {
            try {
                handleLogin();
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
    private void handleLogin() throws Exception {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("Por favor, rellene todos los campos");
        } else {
            boolean isValidUser = usuarioService.validateUser(email, password);

            if (isValidUser) {
                Usuario usuario = usuarioService.findByEmailAndPassword(email, password);
                UsuarioSesion.getInstance().setUserLogged(usuario);
                App.currentController.changeScene(Scenes.MAINPAGE, usuario);
            } else {
                System.out.println("Usuario no encontrado o contraseña incorrecta");
            }
        }
    }

    public void setWelcomeButton() throws Exception {
        App.currentController.changeScene(Scenes.WELCOME, null);
    }
}