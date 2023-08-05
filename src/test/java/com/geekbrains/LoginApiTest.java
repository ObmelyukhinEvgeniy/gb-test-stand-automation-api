package com.geekbrains;

import io.restassured.RestAssured;

import static org.hamcrest.Matchers.*;

import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class LoginApiTest {
    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://test-stand.gb.ru/gateway/login";//Устанавливаем значение урла
    }

    @Test
    @DisplayName("Вход с валидными логином и паролем.")
    void positiveValideData() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", "Evgeniy34")
                .formParam("password", "0e80d10840")
                .when()
                .post(baseURI)
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Вход с не валидным логином и паролем.")
    void negativeNoValideData() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", "badLogin")
                .formParam("password", "666321584")
                .when()
                .post(baseURI)
                .then()
                .statusCode(401)
                .body("error", equalTo("Проверьте логин и пароль."));
    }

    @Test
    @DisplayName("Вход с пустым полем \"Username\" и валидным паролем")
    void negativeFieldEmptyUsername() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", "")
                .formParam("password", "0e80d10840")
                .when()
                .post(baseURI)
                .then()
                .statusCode(401)
                .body("error", equalTo("Проверьте логин и пароль."));
    }

    @Test
    @DisplayName("Вход с пустым полем \"Password\" и валидным логином")
    void negativeFieldEmptyPassword() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", "Evgeniy34")
                .formParam("password", "")
                .when()
                .post(baseURI)
                .then()
                .statusCode(401)
                .body("error", equalTo("Проверьте логин и пароль."));
    }

    @Test
    @DisplayName("Вход с пустыми полями \"Username\" и \"Password\".")
    void negativeFieldsEmpty() throws InterruptedException {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", "")
                .formParam("password", "")
                .when()
                .post(baseURI)
                .then()
                .statusCode(401)
                .body("error", equalTo("Проверьте логин и пароль."));
    }

    @Test
    @DisplayName("Вход с логином включающим спецсимволы и валидным паролем.")
    void negativeFieldsSimvol() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", "<>?)(*&^%$#@!")
                .formParam("password", "b6a463ac06")
                .when()
                .post(baseURI)
                .then()
                .statusCode(401)
                .body("error", equalTo("Проверьте логин и пароль."));
    }

    @Test
    @DisplayName("Вход с логином включающим кирилицу и валидным паролем")
    void negativeFieldUsernameRus() throws InterruptedException {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", "Охсарон")
                .formParam("password", "41d1b9bfa9")
                .when()
                .post(baseURI)
                .then()
                .statusCode(401)
                .body("error", equalTo("Проверьте логин и пароль."));
    }

}
