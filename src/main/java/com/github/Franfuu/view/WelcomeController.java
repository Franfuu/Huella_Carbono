package com.github.Franfuu.view;

import com.github.Franfuu.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController extends Controller implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;

    @Override
    public void onOpen(Object input) throws Exception {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setLoginButton() throws Exception {
        App.currentController.changeScene(Scenes.LOGIN, null);
    }
    public void setRegisterButton() throws Exception {
        App.currentController.changeScene(Scenes.REGISTER, null);
    }

}
