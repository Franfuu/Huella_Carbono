package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.connection.Connection;
import com.github.Franfuu.model.entities.Actividad;
import org.hibernate.Session;

import java.util.List;

public class ActividadDAO {
    // Método para encontrar todas las actividades en la base de datos
    public List<Actividad> findAll() {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        // Selecciona todas las actividades
        List<Actividad> actividades = session.createQuery("from Actividad", Actividad.class).list();
        session.getTransaction().commit();
        session.close();
        return actividades;
    }
}