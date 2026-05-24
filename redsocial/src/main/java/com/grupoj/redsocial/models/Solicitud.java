package com.grupoj.redsocial.models;

import jakarta.persistence.*;
import java.util.Date;

import com.grupoj.redsocial.models.enums.EstadoSolicitud;

@Entity
public class Solicitud {
    @Id
    private String id;

    @ManyToOne
    private Usuario remitente;

    @ManyToOne
    private Usuario destinatario;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public Solicitud() {
    }

    public Solicitud(Usuario remitente, Usuario destinatario) {
        this.id = java.util.UUID.randomUUID().toString();
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.estado = EstadoSolicitud.PENDIENTE;
        this.fecha = new Date();
    }

    public void aceptar() {
        this.estado = EstadoSolicitud.ACEPTADA;
    }
}