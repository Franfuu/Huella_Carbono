package com.github.Franfuu.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * Clase que representa una recomendación en el sistema.
 *
 * Tabla: recomendacion
 * Columnas:
 * - id_recomendacion: Identificador único de la recomendación.
 * - id_categoria: Referencia a la categoría de la recomendación.
 * - descripcion: Descripción de la recomendación.
 * - impacto_estimado: Impacto estimado de la recomendación.
 *
 * Relaciones:
 * - @ManyToOne con Categoria: Muchas recomendaciones pueden pertenecer a una categoría.
 */
@Entity
@Table(name = "recomendacion", schema = "eco")
public class Recomendacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recomendacion", nullable = false)
    private Integer id;

    // Usamos LAZY para idCategoria porque no siempre necesitamos cargar la categoría
    // cuando cargamos una recomendación. Esto mejora el rendimiento.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private Categoria idCategoria;

    @Lob
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "impacto_estimado", nullable = false, precision = 10, scale = 2)
    private BigDecimal impactoEstimado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getImpactoEstimado() {
        return impactoEstimado;
    }

    public void setImpactoEstimado(BigDecimal impactoEstimado) {
        this.impactoEstimado = impactoEstimado;
    }

}