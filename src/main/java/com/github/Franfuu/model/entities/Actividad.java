package com.github.Franfuu.model.entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;
/**
 * Clase que representa una huella en el sistema.
 *
 * Tabla: huella
 * Columnas:
 * - id_registro: Identificador único del registro de huella.
 * - id_usuario: Referencia al usuario que generó la huella.
 * - id_actividad: Referencia a la actividad asociada con la huella.
 * - valor: Valor de la huella.
 * - unidad: Unidad de medida de la huella.
 * - fecha: Fecha en que se registró la huella.
 *
 * Relaciones:
 * - @ManyToOne con Usuario: Muchas huellas pueden pertenecer a un usuario.
 * - @ManyToOne con Actividad: Muchas huellas pueden estar asociadas a una actividad.
 */
@Entity
@Table(name = "actividad", schema = "eco")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    // Usamos LAZY para idCategoria, habitos y huellas porque no siempre necesitamos cargar
    // estas relaciones cuando cargamos una actividad. Esto mejora el rendimiento.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private Categoria idCategoria;

    @OneToMany(mappedBy = "idActividad")
    private Set<Habito> habitos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idActividad")
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

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
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