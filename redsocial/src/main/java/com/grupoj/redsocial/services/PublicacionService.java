package com.grupoj.redsocial.services;

import com.grupoj.redsocial.models.*;
import com.grupoj.redsocial.models.enums.TipoPrivacidad;
import com.grupoj.redsocial.repositories.*;
import org.springframework.stereotype.Service;
import java.util.List; 
import org.springframework.lang.NonNull;

@Service
public class PublicacionService {
    private final UsuarioRepository usuarioRepository;
    private final PublicacionRepository publicacionRepository;

    public PublicacionService(UsuarioRepository usuarioRepository, PublicacionRepository publicacionRepository) {
        this.usuarioRepository = usuarioRepository;
        this.publicacionRepository = publicacionRepository;
    }

    public void crearPublicacion(@NonNull String idAutor, String texto, String privacidad, List<String> idsMencionados) {
        Usuario autor = usuarioRepository.findById(idAutor).orElseThrow();
        List<Usuario> listaMencionados = usuarioRepository.findAllByIdIn(idsMencionados);

        TipoPrivacidad tipoPrivacidad = TipoPrivacidad.valueOf(privacidad); 
        Publicacion pub = new Publicacion(autor, texto, tipoPrivacidad); 

        for (Usuario mencionado : listaMencionados) {
            if (!mencionado.haBloqueadoA(autor) && mencionado.permiteMenciones()) {
                pub.addMencion(mencionado);
            }
        }
        publicacionRepository.save(pub);
    }
}