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

    // Guarda un hábito en la base de datos
    public void saveHabito(Habito habito) {
        habitoDAO.insert(habito);
    }

    // Actualiza un hábito en la base de datos
    public void updateHabito(Habito habito) {
        habitoDAO.update(habito);
    }

    // Elimina un hábito de la base de datos
    public void deleteHabito(Habito habito) {
        habitoDAO.delete(habito);
    }

    // Encuentra todos los hábitos de un usuario
    public List<Habito> findAllByUsuario(Usuario usuario) {
        return habitoDAO.findAllByUsuario(usuario);
    }

    // Obtiene recomendaciones basadas en una huella
    public List<Recomendacion> getRecomendacionesPorHuella(Huella huella) {
        return recomendacionDAO.findByHuellaId(huella.getId());
    }
}