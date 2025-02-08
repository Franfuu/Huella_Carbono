package com.github.Franfuu.services;

import com.github.Franfuu.model.dao.UsuarioDAO;
import com.github.Franfuu.model.entities.Usuario;

import java.util.List;

public class UsuarioService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    // Valida si un usuario existe en la base de datos con el email y contraseña proporcionados
    public boolean validateUser(String email, String password) {
        Usuario usuario = usuarioDAO.findByEmailAndPassword(email, password);
        return usuario != null;
    }

    // Encuentra un usuario en la base de datos por su email y contraseña
    public Usuario findByEmailAndPassword(String email, String password) {
        return usuarioDAO.findByEmailAndPassword(email, password);
    }

    // Guarda un nuevo usuario en la base de datos
    public void saveUser(Usuario usuario) {
        usuarioDAO.insert(usuario);
    }

    // Actualiza un usuario existente en la base de datos
    public void update(Usuario usuario) {
        usuarioDAO.update(usuario);
    }

    // Elimina un usuario de la base de datos
    public void delete(Usuario usuario) {
        usuarioDAO.delete(usuario);
    }

    // Encuentra un usuario en la base de datos por su ID
    public Usuario findById(Integer id) {
        return usuarioDAO.findById(id);
    }

    // Encuentra todos los usuarios en la base de datos
    public List<Usuario> findAll() {
        return usuarioDAO.findAll();
    }
}