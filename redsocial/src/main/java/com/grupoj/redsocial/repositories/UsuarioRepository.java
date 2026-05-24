package com.grupoj.redsocial.repositories;

import com.grupoj.redsocial.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    List<Usuario> findAllByIdIn(List<String> ids);
}