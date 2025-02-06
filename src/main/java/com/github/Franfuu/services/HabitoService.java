package com.github.Franfuu.services;

import com.github.Franfuu.model.dao.RecomendacionDAO;
import com.github.Franfuu.model.entities.Huella;
import com.github.Franfuu.model.entities.Recomendacion;
import com.github.Franfuu.model.connection.Connection;

import java.util.List;

public class HabitoService {

    private final RecomendacionDAO recomendacionDAO;

    public HabitoService() {
        this.recomendacionDAO = new RecomendacionDAO();
    }

    public List<Recomendacion> getRecomendacionesPorHuella(Huella huella) {
        return recomendacionDAO.findByHuellaId(huella.getId());
    }
}