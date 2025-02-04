package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.connection.Connection;
import com.github.Franfuu.model.entities.Actividad;
import org.hibernate.Session;

import java.util.List;

public class ActividadDAO {

    public List<Actividad> findAll() {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        List<Actividad> actividades = session.createQuery("from Actividad", Actividad.class).list();
        session.getTransaction().commit();
        session.close();
        return actividades;
    }
}