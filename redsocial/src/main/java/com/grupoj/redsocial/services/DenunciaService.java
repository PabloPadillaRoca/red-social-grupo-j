package com.grupoj.redsocial.services;

import com.grupoj.redsocial.models.*;
import com.grupoj.redsocial.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull;

@Service
public class DenunciaService {
    private final UsuarioRepository usuarioRepository;
    private final DenunciaRepository denunciaRepository;
    private final PublicacionRepository publicacionRepository;

    public DenunciaService(UsuarioRepository usuarioRepository, DenunciaRepository denunciaRepository, PublicacionRepository publicacionRepository) {
        this.usuarioRepository = usuarioRepository;
        this.denunciaRepository = denunciaRepository;
        this.publicacionRepository = publicacionRepository;
    }

    public void registrarDenuncia(@NonNull String idUsuario, @NonNull String idPub, String motivo) {
        if (denunciaRepository.existeDenuncia(idUsuario, idPub)) return;

        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow();
        Publicacion pub = publicacionRepository.findById(idPub).orElseThrow();

        Denuncia denuncia = new Denuncia(usuario, pub, motivo);
        pub.registrarDenuncia(); 

        denunciaRepository.save(denuncia);
        publicacionRepository.save(pub);
    }
}