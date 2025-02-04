package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.connection.Connection;
import com.github.Franfuu.model.entities.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UsuarioDAO {

    public void insert(Usuario usuario) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
            session.beginTransaction();
            session.save(usuario);
            session.getTransaction().commit();
            session.close();
    }

    public Usuario findById(Integer id) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        Usuario usuario = session.get(Usuario.class, id);
        session.getTransaction().commit();
        session.close();
        return usuario;
    }

    public List<Usuario> findAll() {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        List<Usuario> usuarios = session.createQuery("from Usuario", Usuario.class).list();
        session.getTransaction().commit();
        session.close();
        return usuarios;
    }

    public void update(Usuario usuario) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        session.update(usuario);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(Usuario usuario) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        session.delete(usuario);
        session.getTransaction().commit();
        session.close();
    }

    public Usuario findByUsername(String nombreUsuario) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        Usuario usuario = session.createQuery("from Usuario u where u.nombre = :nombreUsuario", Usuario.class)
                .setParameter("nombreUsuario", nombreUsuario)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return usuario;
    }

    public Usuario findByEmailAndPassword(String email, String password) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        Usuario usuario = session.createQuery("FROM Usuario u WHERE u.email = :email AND u.contraseña = :contraseña", Usuario.class)
                .setParameter("email", email)
                .setParameter("contraseña", password)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return usuario;
    }
}