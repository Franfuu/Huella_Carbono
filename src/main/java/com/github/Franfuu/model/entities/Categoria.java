package com.github.Franfuu.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Clase que representa una categoría en el sistema.
 *
 * Tabla: categoria
 * Columnas:
 * - id_categoria: Identificador único de la categoría.
 * - nombre: Nombre de la categoría.
 * - factor_emision: Factor de emisión asociado a la categoría.
 * - unidad: Unidad de medida del factor de emisión.
 *
 * Relaciones:
 * - @OneToMany con Actividad: Una categoría puede tener múltiples actividades.
 * - @OneToMany con Recomendacion: Una categoría puede tener múltiples recomendaciones.
 */
@Entity
@Table(name = "categoria", schema = "eco")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "factor_emision", nullable = false, precision = 10, scale = 4)
    private BigDecimal factorEmision;

    @Column(name = "unidad", nullable = false, length = 50)
    private String unidad;

    @OneToMany(mappedBy = "idCategoria")
    private Set<Actividad> actividads = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idCategoria")
    private Set<Recomendacion> recomendacions = new LinkedHashSet<>();

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

    public BigDecimal getFactorEmision() {
        return factorEmision;
    }

    public void setFactorEmision(BigDecimal factorEmision) {
        this.factorEmision = factorEmision;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Set<Actividad> getActividads() {
        return actividads;
    }

    public void setActividads(Set<Actividad> actividads) {
        this.actividads = actividads;
    }

    public Set<Recomendacion> getRecomendacions() {
        return recomendacions;
    }

    public void setRecomendacions(Set<Recomendacion> recomendacions) {
        this.recomendacions = recomendacions;
    }

}