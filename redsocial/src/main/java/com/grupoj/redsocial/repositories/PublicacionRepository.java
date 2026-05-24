package com.grupoj.redsocial.repositories;

import com.grupoj.redsocial.models.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacionRepository extends JpaRepository<Publicacion, String> {}