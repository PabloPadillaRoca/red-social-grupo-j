package com.grupoj.redsocial.repositories;

import com.grupoj.redsocial.models.Denuncia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DenunciaRepository extends JpaRepository<Denuncia, String> {
    
    boolean existsByUsuarioIdAndPublicacionId(String idUsuario, String idPub);

    default boolean existeDenuncia(String idUsuario, String idPub) {
        return existsByUsuarioIdAndPublicacionId(idUsuario, idPub);
    }
}