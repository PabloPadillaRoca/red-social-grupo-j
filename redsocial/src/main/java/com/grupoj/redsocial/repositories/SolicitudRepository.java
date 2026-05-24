package com.grupoj.redsocial.repositories;

import com.grupoj.redsocial.models.Solicitud;
import com.grupoj.redsocial.models.enums.EstadoSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SolicitudRepository extends JpaRepository<Solicitud, String> {
    
    Optional<Solicitud> findByRemitenteIdAndDestinatarioIdAndEstado(String idRemitente, String idDestinatario, EstadoSolicitud estado);

    default Optional<Solicitud> findPendiente(String idRemitente, String idDestinatario) {
        return findByRemitenteIdAndDestinatarioIdAndEstado(idRemitente, idDestinatario, EstadoSolicitud.PENDIENTE);
    }
}