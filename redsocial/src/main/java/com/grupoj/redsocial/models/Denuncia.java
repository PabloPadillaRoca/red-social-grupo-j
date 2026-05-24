package com.grupoj.redsocial.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Denuncia {
    @Id
    private String id;

    @ManyToOne
    private Usuario usuario;

    private String motivo;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @ManyToOne
    private Publicacion publicacion;

    public Denuncia() {
    }

    public Denuncia(Usuario usuario, Publicacion publicacion, String motivo) {
        this.id = java.util.UUID.randomUUID().toString();
        this.usuario = usuario;
        this.publicacion = publicacion;
        this.motivo = motivo;
        this.fecha = new Date();
    }
}