package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.entities.Recomendacion;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

import static com.github.Franfuu.model.connection.Connection.sessionFactory;

public class RecomendacionDAO {

    public List<Recomendacion> findByHuellaId(int huellaId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT r FROM Recomendacion r JOIN r.idCategoria c JOIN Huella h ON c.id = h.idActividad.id WHERE h.id = :huellaId";
            Query<Recomendacion> query = session.createQuery(hql, Recomendacion.class);
            query.setParameter("huellaId", huellaId);
            return query.list();
        }
    }
}