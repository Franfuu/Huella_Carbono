// HuellaDAO.java
package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.connection.Connection;
import com.github.Franfuu.model.entities.Huella;
import com.github.Franfuu.model.entities.Usuario;
import org.hibernate.Session;

import java.util.List;

public class HuellaDAO {

    public void insert(Huella huella) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        session.save(huella);
        session.getTransaction().commit();
        session.close();
    }

    public Huella findById(Integer id) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        Huella huella = session.get(Huella.class, id);
        session.getTransaction().commit();
        session.close();
        return huella;
    }

    public List<Huella> findAllByUsuario(Usuario usuario) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        List<Huella> huellas = session.createQuery("from Huella h where h.idUsuario = :usuario", Huella.class)
                .setParameter("usuario", usuario.getId())
                .list();
        session.getTransaction().commit();
        session.close();
        return huellas;
    }

    public void update(Huella huella) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        session.update(huella);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(Huella huella) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        session.delete(huella);
        session.getTransaction().commit();
        session.close();
    }
}