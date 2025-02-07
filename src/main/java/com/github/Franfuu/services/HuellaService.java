package com.github.Franfuu.services;

import com.github.Franfuu.model.dao.HuellaDAO;
import com.github.Franfuu.model.entities.Huella;
import com.github.Franfuu.model.entities.Usuario;

import java.time.Instant;
import java.util.List;

public class HuellaService {

    private final HuellaDAO huellaDAO;

    public HuellaService() {
        this.huellaDAO = new HuellaDAO();
    }

    public void saveHuella(Huella huella) {
        huellaDAO.insert(huella);
    }

    public void updateHuella(Huella huella) {
        huellaDAO.update(huella);
    }

    public void deleteHuella(Huella huella) {
        huellaDAO.delete(huella);
    }

    public List<Huella> findAllByUsuario(Usuario usuario) {
        return huellaDAO.findAllByUsuario(usuario);
    }

    public List<Huella> findByUsuarioAndFechaBetween(Usuario usuario, Instant inicio, Instant fin) {
        return huellaDAO.findByUsuarioAndFechaBetween(usuario, inicio, fin);
    }
}