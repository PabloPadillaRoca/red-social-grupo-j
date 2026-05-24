package com.grupoj.redsocial.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Comentario {
    @Id
    private String id;

    @ManyToOne
    private Usuario usuario; 

    private String texto;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @ManyToOne
    private Publicacion publicacion;

    public Comentario() {
    }

    public Comentario(Usuario usuario, Publicacion publicacion, String texto) {
        this.id = java.util.UUID.randomUUID().toString();
        this.usuario = usuario;
        this.publicacion = publicacion;
        this.texto = texto;
        this.fecha = new Date();
    }
}