package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.connection.Connection;
import org.hibernate.Session;

import java.util.List;

public class GenericDAO<T> {

    private final Class<T> type;

    public GenericDAO(Class<T> type) {
        this.type = type;
    }

    public void insert(T entity) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
    }

    public T findById(Integer id) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        T entity = session.get(type, id);
        session.getTransaction().commit();
        session.close();
        return entity;
    }

    public List<T> findAll() {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        List<T> entities = session.createQuery("from " + type.getName(), type).list();
        session.getTransaction().commit();
        session.close();
        return entities;
    }

    public void update(T entity) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(T entity) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }
}