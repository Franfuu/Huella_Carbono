package com.github.Franfuu.view;

public enum Scenes {
    WELCOME("view/Welcome.fxml"), //ELEGIR ROL
    LOGIN("view/InicioSesionUsuario.fxml"),
    REGISTER("view/RegistroUsuario.fxml"),
    MAINPAGE("view/PaginaPrincipal.fxml"), //PAGINA PRINCIPAL
    ROOT("view/layout.fxml");     //ELIMINAR SALA

    private final String url;

    Scenes(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }
}
