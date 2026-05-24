package com.grupoj.redsocial.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Mencion {
    @Id
    private String id;

    @ManyToOne
    private Publicacion publicacion;

    @ManyToOne
    private Usuario usuarioMencionado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public Mencion() {}
    public Mencion(Publicacion publicacion, Usuario usuarioMencionado) {
        this.id = java.util.UUID.randomUUID().toString();
        this.publicacion = publicacion;
        this.usuarioMencionado = usuarioMencionado;
        this.fecha = new Date();
    }
}