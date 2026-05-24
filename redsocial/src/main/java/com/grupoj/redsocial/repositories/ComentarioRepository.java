package com.grupoj.redsocial.repositories;

import com.grupoj.redsocial.models.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, String> {}