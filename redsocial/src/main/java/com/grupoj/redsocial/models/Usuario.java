package com.grupoj.redsocial.models;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Usuario {
    @Id
    private String id;
    private String username;
    private boolean permiteMenciones;

    @OneToMany(mappedBy = "bloqueador", cascade = CascadeType.ALL)
    private Set<Bloqueo> usuariosBloqueados = new HashSet<>();


    public Usuario() {
    }

    public Usuario(String id, String username, boolean permiteMenciones) {
        this.id = id;
        this.username = username;
        this.permiteMenciones = permiteMenciones;
    }

    public boolean haBloqueadoA(Usuario autor) {
        return usuariosBloqueados.stream()
                .anyMatch(bloqueo -> bloqueo.getBloqueado().getId().equals(autor.getId()));
    }

    public boolean permiteMenciones() {
        return permiteMenciones;
    }

    public void bloquearUsuario(Usuario usuarioABloquear) {
        this.usuariosBloqueados.add(new Bloqueo(this, usuarioABloquear));
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
