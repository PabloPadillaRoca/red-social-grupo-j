package com.grupoj.redsocial.controllers;

import com.grupoj.redsocial.services.AmistadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/amistades")
public class AmistadController {
    private final AmistadService amsService;

    public AmistadController(AmistadService amsService) {
        this.amsService = amsService;
    }

    @PostMapping("/solicitud")
    public ResponseEntity<Void> enviarSolicitud(@RequestParam @NonNull String idRemitente, @RequestParam @NonNull String idDestinatario) {
        amsService.procesarSolicitud(idRemitente, idDestinatario);
        return ResponseEntity.ok().build();
    }
}