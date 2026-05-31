package com.grupoj.redsocial.services;

import com.grupoj.redsocial.models.*;
import com.grupoj.redsocial.models.enums.TipoPrivacidad;
import com.grupoj.redsocial.repositories.*;
import com.grupoj.redsocial.services.PublicacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PublicacionServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PublicacionRepository publicacionRepository;

    @InjectMocks
    private PublicacionService publicacionService;

    private Usuario autor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        autor = new Usuario(); 
    }

    @Test
    void testCrearPublicacion_Camino1_CeroMencionados() {
        when(usuarioRepository.findById("a1")).thenReturn(Optional.of(autor));
        when(usuarioRepository.findAllByIdIn(anyList())).thenReturn(Collections.emptyList());

        publicacionService.crearPublicacion("a1", "Hola", "PUBLICA", Collections.emptyList());

        verify(publicacionRepository).save(any(Publicacion.class));
    }

    @Test
    void testCrearPublicacion_Camino2_MencionadoHaBloqueado() {
        Usuario mencionado = mock(Usuario.class);
        when(usuarioRepository.findById("a1")).thenReturn(Optional.of(autor));
        when(usuarioRepository.findAllByIdIn(anyList())).thenReturn(List.of(mencionado));
        
        when(mencionado.haBloqueadoA(autor)).thenReturn(true); 

        publicacionService.crearPublicacion("a1", "Hola", "PUBLICA", List.of("m1"));

        verify(mencionado, never()).permiteMenciones();
        verify(publicacionRepository).save(any(Publicacion.class));
    }

    @Test
    void testCrearPublicacion_Camino3_NoPermiteMenciones() {
        Usuario mencionado = mock(Usuario.class);
        when(usuarioRepository.findById("a1")).thenReturn(Optional.of(autor));
        when(usuarioRepository.findAllByIdIn(anyList())).thenReturn(List.of(mencionado));
        
        when(mencionado.haBloqueadoA(autor)).thenReturn(false);
        when(mencionado.permiteMenciones()).thenReturn(false);

        publicacionService.crearPublicacion("a1", "Hola", "PUBLICA", List.of("m1"));

        verify(publicacionRepository).save(any(Publicacion.class));
    }

    @Test
    void testCrearPublicacion_Camino4_MencionAñadida() {
        Usuario mencionado = mock(Usuario.class);
        when(usuarioRepository.findById("a1")).thenReturn(Optional.of(autor));
        when(usuarioRepository.findAllByIdIn(anyList())).thenReturn(List.of(mencionado));
        
        when(mencionado.haBloqueadoA(autor)).thenReturn(false);
        when(mencionado.permiteMenciones()).thenReturn(true);

        publicacionService.crearPublicacion("a1", "Hola", "PUBLICA", List.of("m1"));

        verify(mencionado).permiteMenciones(); 
        verify(publicacionRepository).save(any(Publicacion.class));
    }
}