// HabitoDAO.java
package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.connection.Connection;
import com.github.Franfuu.model.entities.Habito;
import com.github.Franfuu.model.entities.Usuario;
import org.hibernate.Session;

import java.util.List;

public class HabitoDAO {

    public void insert(Habito habito) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        session.save(habito);
        session.getTransaction().commit();
        session.close();
    }

    public Habito findById(Integer id) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        Habito habito = session.get(Habito.class, id);
        session.getTransaction().commit();
        session.close();
        return habito;
    }

    public void update(Habito habito) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        session.update(habito);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(Habito habito) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        session.delete(habito);
        session.getTransaction().commit();
        session.close();
    }

    public List<Habito> findAllByUsuario(Usuario usuario) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        List<Habito> habitos = session.createQuery("from Habito h where h.idUsuario = :usuario", Habito.class)
                .setParameter("usuario", usuario)
                .list();
        session.getTransaction().commit();
        session.close();
        return habitos;
    }
}