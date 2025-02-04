package com.github.Franfuu.view;

public enum Scenes {
    WELCOME("view/Welcome.fxml"), //ELEGIR ROL
    LOGIN("view/InicioSesionUsuario.fxml"),
    REGISTER("view/RegistroUsuario.fxml"),
    MAINPAGE("view/PaginaPrincipal.fxml"),
    ADDHUELLA("view/AgregarHuella.fxml"),
    ADDHABITO("view/AgregarHabito.fxml"),
    ROOT("view/layout.fxml");

    private final String url;

    Scenes(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }
}
