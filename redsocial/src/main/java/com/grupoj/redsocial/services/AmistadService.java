package com.grupoj.redsocial.services;

import com.grupoj.redsocial.models.*;
import com.grupoj.redsocial.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull;

@Service
public class AmistadService {
    private final UsuarioRepository usuarioRepository;
    private final RelacionRepository relacionRepository;
    private final SolicitudRepository solicitudRepository;

    public AmistadService(UsuarioRepository usuarioRepository, RelacionRepository relacionRepository, SolicitudRepository solicitudRepository) {
        this.usuarioRepository = usuarioRepository;
        this.relacionRepository = relacionRepository;
        this.solicitudRepository = solicitudRepository;
    }

    public void procesarSolicitud(@NonNull String idRemitente, @NonNull String idDestinatario) {
        if (relacionRepository.existeBloqueo(idRemitente, idDestinatario)) return;
        if (relacionRepository.sonAmigos(idRemitente, idDestinatario)) return;

        var solicitudInversa = solicitudRepository.findPendiente(idDestinatario, idRemitente);

        Usuario remitente = usuarioRepository.findById(idRemitente).orElseThrow();
        Usuario destinatario = usuarioRepository.findById(idDestinatario).orElseThrow();

        if (solicitudInversa.isEmpty()) {
            Solicitud solicitud = new Solicitud(remitente, destinatario);
            solicitudRepository.save(solicitud);
        } else {
            Solicitud existente = solicitudInversa.get();
            existente.aceptar();
            solicitudRepository.save(existente);

            Amistad nuevaAmistad = new Amistad(remitente, destinatario);
            relacionRepository.save(nuevaAmistad);
        }
    }
}