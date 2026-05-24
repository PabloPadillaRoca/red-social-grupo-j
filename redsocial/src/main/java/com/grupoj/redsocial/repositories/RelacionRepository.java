package com.grupoj.redsocial.repositories;

import com.grupoj.redsocial.models.Amistad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RelacionRepository extends JpaRepository<Amistad, String> {

    boolean existsByRemitenteIdAndDestinatarioId(String idRemitente, String idDestinatario);
    
    default boolean sonAmigos(String idRemitente, String idDestinatario) {
        return existsByRemitenteIdAndDestinatarioId(idRemitente, idDestinatario) || 
               existsByRemitenteIdAndDestinatarioId(idDestinatario, idRemitente);
    }

    @Query("SELECT COUNT(b) > 0 FROM Bloqueo b WHERE b.bloqueador.id = :idRemitente AND b.bloqueado.id = :idDestinatario")
    boolean existeBloqueo(@Param("idRemitente") String idRemitente, @Param("idDestinatario") String idDestinatario);
}