// HabitoService.java
package com.github.Franfuu.services;

import com.github.Franfuu.model.dao.HabitoDAO;
import com.github.Franfuu.model.dao.RecomendacionDAO;
import com.github.Franfuu.model.entities.Habito;

import com.github.Franfuu.model.entities.Huella;
import com.github.Franfuu.model.entities.Recomendacion;
import com.github.Franfuu.model.entities.Usuario;

import java.util.List;

public class HabitoService {

    private final HabitoDAO habitoDAO;
    private final RecomendacionDAO recomendacionDAO;

    public HabitoService() {
        this.habitoDAO = new HabitoDAO();
        this.recomendacionDAO = new RecomendacionDAO();
    }

    public void saveHabito(Habito habito) {
        habitoDAO.insert(habito);
    }

    public void updateHabito(Habito habito) {
        habitoDAO.update(habito);
    }

    public void deleteHabito(Habito habito) {
        habitoDAO.delete(habito);
    }

    public List<Habito> findAllByUsuario(Usuario usuario) {
        return habitoDAO.findAllByUsuario(usuario);
    }

    public List<Recomendacion> getRecomendacionesPorHuella(Huella huella) {
        return recomendacionDAO.findByHuellaId(huella.getId());
    }
}