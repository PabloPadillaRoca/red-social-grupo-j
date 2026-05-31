package com.grupoj.redsocial.services;

import com.grupoj.redsocial.models.*;
import com.grupoj.redsocial.repositories.*;
import com.grupoj.redsocial.services.AmistadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AmistadServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private RelacionRepository relacionRepository;
    @Mock
    private SolicitudRepository solicitudRepository;

    @InjectMocks
    private AmistadService amistadService;

    private Usuario remitente;
    private Usuario destinatario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        remitente = new Usuario();
        destinatario = new Usuario();
    }

    @Test
    void testProcesarSolicitud_Camino1_ExisteBloqueo() {
        when(relacionRepository.existeBloqueo("r1", "d1")).thenReturn(true);

        amistadService.procesarSolicitud("r1", "d1");

        verify(relacionRepository).existeBloqueo("r1", "d1");
        verifyNoInteractions(usuarioRepository, solicitudRepository);
    }

    @Test
    void testProcesarSolicitud_Camino2_SonAmigos() {
        when(relacionRepository.existeBloqueo("r1", "d1")).thenReturn(false);
        when(relacionRepository.sonAmigos("r1", "d1")).thenReturn(true);

        amistadService.procesarSolicitud("r1", "d1");

        verify(relacionRepository).sonAmigos("r1", "d1");
        verifyNoInteractions(usuarioRepository, solicitudRepository);
    }

    @Test
    void testProcesarSolicitud_Camino3_SinSolicitudInversa() {
        when(relacionRepository.existeBloqueo("r1", "d1")).thenReturn(false);
        when(relacionRepository.sonAmigos("r1", "d1")).thenReturn(false);
        when(solicitudRepository.findPendiente("d1", "r1")).thenReturn(Optional.empty());
        when(usuarioRepository.findById("r1")).thenReturn(Optional.of(remitente));
        when(usuarioRepository.findById("d1")).thenReturn(Optional.of(destinatario));

        amistadService.procesarSolicitud("r1", "d1");

        verify(solicitudRepository).save(any(Solicitud.class));
        verify(relacionRepository, never()).save(any(Amistad.class));
    }

    @Test
    void testProcesarSolicitud_Camino4_ConSolicitudInversa() {
        Solicitud solicitudInversa = mock(Solicitud.class);
        when(relacionRepository.existeBloqueo("r1", "d1")).thenReturn(false);
        when(relacionRepository.sonAmigos("r1", "d1")).thenReturn(false);
        when(solicitudRepository.findPendiente("d1", "r1")).thenReturn(Optional.of(solicitudInversa));
        when(usuarioRepository.findById("r1")).thenReturn(Optional.of(remitente));
        when(usuarioRepository.findById("d1")).thenReturn(Optional.of(destinatario));

        amistadService.procesarSolicitud("r1", "d1");

        verify(solicitudInversa).aceptar();
        verify(solicitudRepository).save(solicitudInversa);
        verify(relacionRepository).save(any(Amistad.class));
    }
}