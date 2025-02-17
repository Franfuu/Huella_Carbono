package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.connection.Connection;
import com.github.Franfuu.model.entities.Huella;
import com.github.Franfuu.model.entities.Usuario;
import org.hibernate.Session;

import java.time.Instant;
import java.util.List;

public class HuellaDAO {

    // Método para insertar una nueva huella en la base de datos
    public void insert(Huella huella) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        session.save(huella);
        session.getTransaction().commit();
        session.close();
    }

    // Método para encontrar una huella por su ID
    public Huella findById(Integer id) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        Huella huella = session.get(Huella.class, id);
        session.getTransaction().commit();
        session.close();
        return huella;
    }

    // Método para encontrar todas las huellas asociadas a un usuario específico
    public List<Huella> findAllByUsuario(Usuario usuario) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        // Selecciona todas las huellas asociadas a un usuario específico
        List<Huella> huellas = session.createQuery(
                        "from Huella h " +
                                "join fetch h.idActividad a " +
                                "join fetch a.idCategoria " +
                                "where h.idUsuario = :usuario", Huella.class)
                .setParameter("usuario", usuario)
                .list();
        session.getTransaction().commit();
        session.close();
        return huellas;
    }

    // Método para encontrar todas las huellas por el ID del usuario
    public List<Huella> findAll(String usuarioId) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        // Selecciona todas las huellas asociadas a un usuario específico
        List<Huella> huellas = session.createQuery(
                        "SELECT h FROM Huella h " +
                                "JOIN FETCH h.idUsuario u " +
                                "JOIN FETCH h.idActividad a " +
                                "JOIN FETCH a.idCategoria " +
                                "WHERE u.id = :usuarioId", Huella.class)
                .setParameter("usuarioId", usuarioId)
                .list();
        session.getTransaction().commit();
        session.close();
        return huellas;
    }
    // Método para encontrar huellas con actividad y categoría asociadas a un usuario específico
    public List<Object[]> findHuellaWithActividadAndCategoria(Usuario usuario) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        // Selecciona todas las huellas asociadas a un usuario específico con actividad y categoría
        List<Object[]> results = session.createQuery(
                        "SELECT h.valor, c.factorEmision, c.nombre " +
                                "FROM Huella h " +
                                "JOIN h.idActividad a " +
                                "JOIN a.idCategoria c " +
                                "WHERE h.idUsuario = :usuario", Object[].class)
                .setParameter("usuario", usuario)
                .list();
        session.getTransaction().commit();
        session.close();
        return results;
    }

    // Método para encontrar huellas de un usuario en un rango de fechas específico
    public List<Huella> findByUsuarioAndFechaBetween(Usuario usuario, Instant inicio, Instant fin) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        // Selecciona todas las huellas asociadas a un usuario específico en un rango de fechas específico
        List<Huella> huellas = session.createQuery(
                        "from Huella h " +
                                "join fetch h.idActividad a " +
                                "join fetch a.idCategoria " +
                                "where h.idUsuario = :usuario and h.fecha between :inicio and :fin", Huella.class)
                .setParameter("usuario", usuario)
                .setParameter("inicio", inicio)
                .setParameter("fin", fin)
                .list();
        session.getTransaction().commit();
        session.close();
        return huellas;
    }

    // Método para actualizar una huella existente en la base de datos
    public void update(Huella huella) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        session.update(huella);
        session.getTransaction().commit();
        session.close();
    }

    // Método para eliminar una huella de la base de datos
    public void delete(Huella huella) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        session.delete(huella);
        session.getTransaction().commit();
        session.close();
    }
}