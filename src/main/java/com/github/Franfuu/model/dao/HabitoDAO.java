package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.connection.Connection;
import com.github.Franfuu.model.entities.Habito;
import com.github.Franfuu.model.entities.HabitoId;
import com.github.Franfuu.model.entities.Usuario;
import org.hibernate.Session;

import java.util.List;

public class HabitoDAO {

    // Método para insertar un nuevo hábito en la base de datos
    public void insert(Habito habito) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        session.save(habito);
        session.getTransaction().commit();
        session.close();
    }

    // Método para encontrar un hábito por su ID
    public Habito findById(HabitoId id) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        Habito habito = session.get(Habito.class, id);
        session.getTransaction().commit();
        session.close();
        return habito;
    }

    // Método para actualizar un hábito existente en la base de datos
    public void update(Habito habito) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        session.update(habito);
        session.getTransaction().commit();
        session.close();
    }

    // Método para eliminar un hábito de la base de datos
    public void delete(Habito habito) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        session.delete(habito);
        session.getTransaction().commit();
        session.close();
    }

    // Método para encontrar todos los hábitos asociados a un usuario específico
    public List<Habito> findAllByUsuario(Usuario usuario) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        // Selecciona todos los hábitos asociados a un usuario específico
        List<Habito> habitos = session.createQuery("from Habito h where h.idUsuario = :usuario", Habito.class)
                .setParameter("usuario", usuario)
                .list();
        session.getTransaction().commit();
        session.close();
        return habitos;
    }
}