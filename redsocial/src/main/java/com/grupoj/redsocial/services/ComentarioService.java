package com.grupoj.redsocial.services;

import com.grupoj.redsocial.models.*;
import com.grupoj.redsocial.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull;

@Service
public class ComentarioService {
    private final UsuarioRepository usuarioRepository;
    private final PublicacionRepository publicacionRepository;
    private final RelacionRepository relacionRepository;
    private final ComentarioRepository comentarioRepository;

    public ComentarioService(UsuarioRepository usuarioRepository, PublicacionRepository publicacionRepository, RelacionRepository relacionRepository, ComentarioRepository comentarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.publicacionRepository = publicacionRepository;
        this.relacionRepository = relacionRepository;
        this.comentarioRepository = comentarioRepository;
    }

    public void crearComentario(@NonNull String idUsuario, @NonNull String idPub, String texto) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow();
        Publicacion pub = publicacionRepository.findById(idPub).orElseThrow();
        boolean sonAmigos = relacionRepository.sonAmigos(idUsuario, pub.getAutor().getId());

        if (pub.puedeComentar(idUsuario, sonAmigos)) {
            Comentario comentario = new Comentario(usuario, pub, texto);
            pub.incrementarComentarios();

            comentarioRepository.save(comentario);
            publicacionRepository.save(pub); 
        }
    }
}