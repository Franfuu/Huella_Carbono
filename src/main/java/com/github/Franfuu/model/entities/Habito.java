package com.github.Franfuu.model.entities;

import jakarta.persistence.*;

import java.time.Instant;

/**
 * Clase que representa un hábito en el sistema.
 *
 * Tabla: habito
 * Columnas:
 * - id_usuario: Referencia al usuario que tiene el hábito.
 * - id_actividad: Referencia a la actividad asociada con el hábito.
 * - frecuencia: Frecuencia del hábito.
 * - tipo: Tipo de hábito.
 * - ultima_fecha: Última fecha en que se realizó el hábito.
 *
 * Relaciones:
 * - @ManyToOne con Usuario: Muchos hábitos pueden pertenecer a un usuario.
 * - @ManyToOne con Actividad: Muchos hábitos pueden estar asociados a una actividad.
 */
@Entity
@Table(name = "habito", schema = "eco")
public class Habito {
    @EmbeddedId
    private HabitoId id;

    // Usamos EAGER para idUsuario e idActividad porque siempre necesitamos conocer el usuario
    // y la actividad asociados con un hábito cuando lo cargamos. Esto evita múltiples consultas
    // adicionales a la base de datos.

    @MapsId("idUsuario")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario idUsuario;

    @MapsId("idActividad")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_actividad", nullable = false)
    private Actividad idActividad;

    @Column(name = "frecuencia", nullable = false)
    private Integer frecuencia;

    @Lob
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "ultima_fecha")
    private Instant ultimaFecha;

    public HabitoId getId() {
        return id;
    }

    public void setId(HabitoId id) {
        this.id = id;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Actividad getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Actividad idActividad) {
        this.idActividad = idActividad;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Instant getUltimaFecha() {
        return ultimaFecha;
    }

    public void setUltimaFecha(Instant ultimaFecha) {
        this.ultimaFecha = ultimaFecha;
    }

}