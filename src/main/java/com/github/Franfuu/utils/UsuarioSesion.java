package com.github.Franfuu.utils;

import com.github.Franfuu.model.entities.Usuario;

public class UsuarioSesion {
    // Instancia única de la sesión
    private static UsuarioSesion _instance;
    // Cliente actualmente logueado
    private static Usuario userLogged;

    // Constructor privado para evitar instanciación externa
    private UsuarioSesion() {

    }

    // Obtiene la instancia única de la sesión
    public static UsuarioSesion getInstance() {
        if (_instance == null) {
            _instance = new UsuarioSesion();
            _instance.logIn(userLogged);
        }
        return _instance;
    }

    // Inicia sesión con un cliente
    public void logIn(Usuario client) {
        userLogged = client;
    }

    // Obtiene el cliente actualmente logueado
    public Usuario getUserLogged() {
        return userLogged;
    }
    public void setUserLogged(Usuario userLogged) {
        this.userLogged = userLogged;
    }

    // Cierra la sesión
    public void logOut() {
        userLogged = null;
    }
}
