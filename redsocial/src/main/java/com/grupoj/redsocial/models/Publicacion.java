package com.grupoj.redsocial.models;

import com.grupoj.redsocial.models.enums.*;
import jakarta.persistence.*;
import java.util.*;

@Entity
public class Publicacion {
    @Id
    private String id;
    private String texto;
    private int contadorDenuncias;

    @Enumerated(EnumType.STRING)
    private TipoPrivacidad privacidad;

    @Enumerated(EnumType.STRING)
    private EstadoPublicacion estado;

    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL)
    private List<Mencion> menciones = new ArrayList<>();

    private int contadorComentarios = 0;

    @ManyToOne
    private Usuario autor;

    public Publicacion() {
    }

    public Publicacion(Usuario autor, String texto, TipoPrivacidad privacidad) {
        this.id = UUID.randomUUID().toString();
        this.autor = autor;
        this.texto = texto;
        this.privacidad = privacidad;
        this.estado = EstadoPublicacion.VISIBLE;
        this.contadorDenuncias = 0;
    }

    // Reglas de negocio (Experto en Informacion)
    public void addMencion(Usuario usuario) {
        this.menciones.add(new Mencion(this, usuario));
    }

    public boolean puedeComentar(String idUsuario, boolean sonAmigos) {
        if (this.estado == EstadoPublicacion.OCULTA) {
            return false;
        }

        if (this.privacidad == TipoPrivacidad.SOLO_AMIGOS && !sonAmigos && !this.autor.getId().equals(idUsuario)) {
            return false;
        }
        return true;
    }

    public void registrarDenuncia() {
        this.contadorDenuncias++;
        if (this.contadorDenuncias >= 3) { // Umbral de ejemplo
            this.estado = EstadoPublicacion.OCULTA;
        }
    }

    public String getId() {
        return id;
    }

    public Usuario getAutor() {
        return autor;
    }

    public EstadoPublicacion getEstado() {
        return estado;
    }

    public void incrementarComentarios() {
        this.contadorComentarios++;
    }
}