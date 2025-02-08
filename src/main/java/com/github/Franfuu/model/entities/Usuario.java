package com.github.Franfuu.model.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Clase que representa a un usuario en el sistema.
 *
 * Tabla: usuario
 * Columnas:
 * - id_usuario: Identificador único del usuario.
 * - nombre: Nombre del usuario.
 * - email: Correo electrónico del usuario.
 * - contraseña: Contraseña del usuario.
 * - fecha_registro: Fecha de registro del usuario.
 *
 * Relaciones:
 * - @OneToMany con Habito: Un usuario puede tener múltiples hábitos.
 * - @OneToMany con Huella: Un usuario puede tener múltiples huellas.
 */
@Entity
@Table(name = "usuario", schema = "eco")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "`contraseña`", nullable = false)
    private String contraseña;

    @ColumnDefault("current_timestamp()")
    @Column(name = "fecha_registro")
    private Instant fechaRegistro;

    @OneToMany(mappedBy = "idUsuario")
    private Set<Habito> habitos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idUsuario")
    private Set<Huella> huellas = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Instant getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Instant fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Set<Habito> getHabitos() {
        return habitos;
    }

    public void setHabitos(Set<Habito> habitos) {
        this.habitos = habitos;
    }

    public Set<Huella> getHuellas() {
        return huellas;
    }

    public void setHuellas(Set<Huella> huellas) {
        this.huellas = huellas;
    }

    @Override
    public String toString() {
        return nombre;
    }

}