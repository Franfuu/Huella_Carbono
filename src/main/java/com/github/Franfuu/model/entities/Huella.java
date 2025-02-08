package com.github.Franfuu.model.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
@Table(name = "huella", schema = "eco")
public class Huella {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro", nullable = false)
    private Integer id;

    // Usamos EAGER para idUsuario e idActividad porque siempre necesitamos conocer el usuario
    // y la actividad asociados con una huella cuando la cargamos. Esto evita múltiples consultas
    // adicionales a la base de datos.

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private Usuario idUsuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_actividad")
    private Actividad idActividad;

    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "unidad", nullable = false, length = 50)
    private String unidad;

    @ColumnDefault("current_timestamp()")
    @Column(name = "fecha")
    private Instant fecha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return idActividad.getNombre();
    }

    public LocalDate getFechaAsLocalDate() {
        Instant instant = getFecha();
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }
}