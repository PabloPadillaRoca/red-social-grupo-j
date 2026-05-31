package com.grupoj.redsocial.controllers;


import com.grupoj.redsocial.controllers.ComentarioController;
import com.grupoj.redsocial.services.ComentarioService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

class ComentarioControllerTest {

    private ComentarioService comentarioService;

    @BeforeEach
    void setUp() {
        comentarioService = Mockito.mock(ComentarioService.class);
        RestAssuredMockMvc.standaloneSetup(new ComentarioController(comentarioService));
    }

    @Test
    void testComentar_TodosParametrosValidos_CE1() {
        given()
            .param("idUsuario", "user1")
            .param("idPub", "pub1")
            .param("texto", "¡Gran publicación!")
        .when()
            .post("/api/comentarios")
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void testComentar_FaltaIdUsuario_CE2() {
        given()
            .param("idPub", "pub1")
            .param("texto", "¡Gran publicación!")
        .when()
            .post("/api/comentarios")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testComentar_FaltaIdPub_CE3() {
        given()
            .param("idUsuario", "user1")
            .param("texto", "¡Gran publicación!")
        .when()
            .post("/api/comentarios")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testComentar_FaltaTexto_CE4() {
        given()
            .param("idUsuario", "user1")
            .param("idPub", "pub1")
        .when()
            .post("/api/comentarios")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}