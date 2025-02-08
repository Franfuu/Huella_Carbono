package com.github.Franfuu.model.dao;

import com.github.Franfuu.model.connection.Connection;
import com.github.Franfuu.model.entities.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UsuarioDAO {

    // Método para insertar un nuevo usuario en la base de datos
    public void insert(Usuario usuario) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        session.save(usuario);
        session.getTransaction().commit();
        session.close();
    }

    // Método para encontrar un usuario por su ID
    public Usuario findById(Integer id) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        Usuario usuario = session.get(Usuario.class, id);
        session.getTransaction().commit();
        session.close();
        return usuario;
    }

    // Método para encontrar todos los usuarios en la base de datos
    public List<Usuario> findAll() {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        // Selecciona todos los usuarios
        List<Usuario> usuarios = session.createQuery("from Usuario", Usuario.class).list();
        session.getTransaction().commit();
        session.close();
        return usuarios;
    }

    // Método para actualizar un usuario existente en la base de datos
    public void update(Usuario usuario) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        session.update(usuario);
        session.getTransaction().commit();
        session.close();
    }

    // Método para eliminar un usuario de la base de datos
    public void delete(Usuario usuario) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        session.delete(usuario);
        session.getTransaction().commit();
        session.close();
    }

    // Método para encontrar un usuario por su nombre de usuario
    public Usuario findByUsername(String nombreUsuario) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        // Selecciona un usuario por su nombre de usuario
        Usuario usuario = session.createQuery("from Usuario u where u.nombre = :nombreUsuario", Usuario.class)
                .setParameter("nombreUsuario", nombreUsuario)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return usuario;
    }

    // Método para encontrar un usuario por su email y contraseña
    public Usuario findByEmailAndPassword(String email, String password) {
        Connection connection = Connection.getInstance();
        Session session = connection.getInstance().getSessionFactory();
        session.beginTransaction();
        // Selecciona un usuario por su email y contraseña
        Usuario usuario = session.createQuery("FROM Usuario u WHERE u.email = :email AND u.contraseña = :contraseña", Usuario.class)
                .setParameter("email", email)
                .setParameter("contraseña", password)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return usuario;
    }
}