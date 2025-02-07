package com.github.Franfuu.services;

import com.github.Franfuu.model.dao.UsuarioDAO;
import com.github.Franfuu.model.entities.Usuario;

import java.util.List;

public class UsuarioService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean validateUser(String email, String password) {
        Usuario usuario = usuarioDAO.findByEmailAndPassword(email, password);
        return usuario != null;
    }

    public Usuario findByEmailAndPassword(String email, String password) {
        return usuarioDAO.findByEmailAndPassword(email, password);
    }

    public void saveUser(Usuario usuario) {
        usuarioDAO.insert(usuario);
    }

    public void update(Usuario usuario) {
        usuarioDAO.update(usuario);
    }

    public void delete(Usuario usuario) {
        usuarioDAO.delete(usuario);
    }

    public Usuario findById(Integer id) {
        return usuarioDAO.findById(id);
    }

    public List<Usuario> findAll() {
        return usuarioDAO.findAll();
    }
}