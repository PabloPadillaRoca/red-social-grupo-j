package com.grupoj.redsocial.controllers;

import com.grupoj.redsocial.controllers.DenunciaController;
import com.grupoj.redsocial.services.DenunciaService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

class DenunciaControllerTest {

    private DenunciaService denunciaService;

    @BeforeEach
    void setUp() {
        denunciaService = Mockito.mock(DenunciaService.class);
        RestAssuredMockMvc.standaloneSetup(new DenunciaController(denunciaService));
    }

    @Test
    void testDenunciar_TodosParametrosValidos_CE1() {
        given()
            .param("idUsuario", "user1")
            .param("idPub", "pub1")
            .param("motivo", "Spam")
        .when()
            .post("/api/denuncias")
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void testDenunciar_FaltaIdUsuario_CE2() {
        given()
            .param("idPub", "pub1")
            .param("motivo", "Spam")
        .when()
            .post("/api/denuncias")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testDenunciar_FaltaIdPub_CE3() {
        given()
            .param("idUsuario", "user1")
            .param("motivo", "Spam")
        .when()
            .post("/api/denuncias")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testDenunciar_FaltaMotivo_CE4() {
        given()
            .param("idUsuario", "user1")
            .param("idPub", "pub1")
        .when()
            .post("/api/denuncias")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}