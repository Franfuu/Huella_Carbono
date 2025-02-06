package com.github.Franfuu.view;

import com.github.Franfuu.model.entities.Usuario;
import com.github.Franfuu.services.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditUserController extends Controller {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button saveButton;

    private Usuario usuario;
    private UsuarioService usuarioService = new UsuarioService();

    public void setUser(Usuario usuario) {
        this.usuario = usuario;
        nameField.setText(usuario.getNombre());
        emailField.setText(usuario.getEmail());
        passwordField.setText(usuario.getContraseña());
    }

    @FXML
    private void saveUser() throws Exception {
        usuario.setNombre(nameField.getText());
        usuario.setEmail(emailField.getText());
        usuario.setContraseña(passwordField.getText());

        usuarioService.update(usuario);

        // Close the modal
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    @Override
    public void onOpen(Object input) throws Exception {

    }

    @Override
    public void onClose(Object output) {

    }
}