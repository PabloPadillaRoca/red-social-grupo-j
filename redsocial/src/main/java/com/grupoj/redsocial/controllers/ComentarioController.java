package com.grupoj.redsocial.controllers;

import com.grupoj.redsocial.services.ComentarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@RestController 
@RequestMapping("/api/comentarios")
public class ComentarioController {
    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @PostMapping
    public ResponseEntity<Void> comentar(@RequestParam @NonNull String idUsuario, @RequestParam @NonNull String idPub,
            @RequestParam String texto) {
        comentarioService.crearComentario(idUsuario, idPub, texto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}