package com.grupoj.redsocial.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Amistad {
    @Id
    private String id;
    
    @ManyToOne
    private Usuario remitente;

    @ManyToOne
    private Usuario destinatario;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;

    public Amistad() {}
    public Amistad(Usuario remitente, Usuario destinatario) {
        this.id = java.util.UUID.randomUUID().toString();
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.fechaInicio = new Date();
    }
}