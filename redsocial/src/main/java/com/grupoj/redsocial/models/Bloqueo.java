package com.grupoj.redsocial.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Bloqueo {
    @Id
    private String id;

    @ManyToOne
    private Usuario bloqueador;

    @ManyToOne
    private Usuario bloqueado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha; 

    public Bloqueo() {
    }

    public Bloqueo(Usuario bloqueador, Usuario bloqueado) {
        this.id = java.util.UUID.randomUUID().toString();
        this.bloqueador = bloqueador;
        this.bloqueado = bloqueado;
        this.fecha = new Date();
    }

    public String getId() {
        return id;
    }

    public Usuario getBloqueador() {
        return bloqueador;
    }

    public Usuario getBloqueado() {
        return bloqueado;
    }

    public Date getFecha() {
        return fecha;
    }
}