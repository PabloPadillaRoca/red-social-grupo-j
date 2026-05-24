package com.grupoj.redsocial.controllers;

import com.grupoj.redsocial.services.PublicacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {
    private final PublicacionService publicacionService;

    public PublicacionController(PublicacionService publicacionService) {
        this.publicacionService = publicacionService;
    }

    @PostMapping
    public ResponseEntity<Void> publicar(@RequestParam @NonNull String idAutor, @RequestParam String texto,
            @RequestParam String privacidad, @RequestParam List<String> idsMencionados) {
        publicacionService.crearPublicacion(idAutor, texto, privacidad, idsMencionados);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}