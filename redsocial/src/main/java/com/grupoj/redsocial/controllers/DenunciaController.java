package com.grupoj.redsocial.controllers;

import com.grupoj.redsocial.services.DenunciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/denuncias")
public class DenunciaController {
    private final DenunciaService denunciaService;

    public DenunciaController(DenunciaService denunciaService) {
        this.denunciaService = denunciaService;
    }

    @PostMapping
    public ResponseEntity<Void> denunciar(@RequestParam @NonNull String idUsuario, @RequestParam @NonNull String idPub,
            @RequestParam String motivo) {
        denunciaService.registrarDenuncia(idUsuario, idPub, motivo);
        return ResponseEntity.ok().build();
    }
}