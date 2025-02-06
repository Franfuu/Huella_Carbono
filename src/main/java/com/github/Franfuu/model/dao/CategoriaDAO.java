package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.connection.Connection;
import com.github.Franfuu.model.entities.Categoria;
import org.hibernate.Session;

import java.util.List;

public class CategoriaDAO {

    public List<Categoria> findAll() {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        List<Categoria> categorias = session.createQuery("from Categoria", Categoria.class).list();
        session.getTransaction().commit();
        session.close();
        return categorias;
    }
}