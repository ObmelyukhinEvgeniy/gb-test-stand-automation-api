package com.geekbrains;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class OthersPostsApiTest {
    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://test-stand.gb.ru/api/posts";//Устанавливаем значение урла
    }

    @Test
    @DisplayName("Запрос с параметрами:\"order\"=\"ASC\", \"page\"= 1")
    void smokeASCTest() {
        RestAssured.given()
                .header("X-Auth-Token", "4cab6cf7e5263d8c3e578ce38eee5cff")
                .queryParam("owner", "notMe")//Устанавливаем query параметры ключ/значение
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .queryParam("page", 1)
                .when()
                .get(baseURI)
                .then()
                .statusCode(200)//Проверка на код 200
                .contentType(ContentType.JSON)//В ответе приходит JSON
                .body("data", not(empty()))//Поле 'data' не является пустым
                .body("data", everyItem(hasKey("id")))//В массиве 'data' содержится поле 'id'
                .body("data", everyItem(hasKey("title")))
                .body("data", everyItem(hasKey("description")))
                .body("data", everyItem(hasKey("content")))
                .body("data.size()", equalTo(4))//Размер массива 'data' равен ожидаемому значению 4.
                .body("meta", hasKey("prevPage"))//В массиве 'meta' содержится поле 'prevPage'
                .body("meta.prevPage", instanceOf(String.class))//Тип данных в массиве 'meta' у параметра 'prevPage' является String
                .body("meta", hasKey("nextPage"))
                .body("meta.nextPage", instanceOf(String.class))//Тип данных в массиве 'meta' у параметра 'nextPage' является String
                .body("meta", hasKey("count"))
                .body("meta.count", anyOf(instanceOf(Integer.class)));//Тип данных в массиве 'meta' у параметра 'count' является Integer

    }
    @Test
    @DisplayName("Запрос с параметрами:\"order\"=\"DESC\", \"page\"= 1")
    void criticalPathDESCTest() {
        given()
                .header("X-Auth-Token", "4cab6cf7e5263d8c3e578ce38eee5cff")
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "DESC")
                .queryParam("page", 1)
                .when()
                .get(baseURI)
                .then()
                .statusCode(200)//Проверка на код 200
                .contentType(ContentType.JSON)//В ответе приходит JSON
                .body("data", not(empty()))//Поле 'data' не является пустым
                .body("data", everyItem(hasKey("id")))//В массиве 'data' содержится поле 'id'
                .body("data", everyItem(hasKey("title")))
                .body("data", everyItem(hasKey("description")))
                .body("data", everyItem(hasKey("content")))
                .body("data.size()", equalTo(4))//Размер массива 'data' равен ожидаемому значению 4.
                .body("meta", hasKey("prevPage"))//В массиве 'meta' содержится поле 'prevPage'
                .body("meta.prevPage", instanceOf(String.class))//Тип данных в массиве 'meta' у параметра 'prevPage' является String
                .body("meta", hasKey("nextPage"))
                .body("meta.nextPage", instanceOf(String.class))//Тип данных в массиве 'meta' у параметра 'nextPage' является String
                .body("meta", hasKey("count"))
                .body("meta.count", anyOf(instanceOf(Integer.class)));//Тип данных в массиве 'meta' у параметра 'count' является Integer
    }
    @Test
    @DisplayName("Запрос с параметрами:\"order\"=\"ALL\", \"page\"= 1")
    void criticalPathALLTest() {
        given()
                .header("X-Auth-Token", "4cab6cf7e5263d8c3e578ce38eee5cff")
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ALL")
                .queryParam("page", 1)
                .when()
                .get(baseURI)
                .then()
                .statusCode(200)//Проверка на код 200
                .contentType(ContentType.JSON)//В ответе приходит JSON
                .body("data", not(empty()))//Поле 'data' не является пустым
                .body("data", everyItem(hasKey("id")))//В массиве 'data' содержится поле 'id'
                .body("data", everyItem(hasKey("title")))
                .body("data", everyItem(hasKey("description")))
                .body("data", everyItem(hasKey("content")))
                .body("data.size()", equalTo(4))//Размер массива 'data' равен ожидаемому значению 4.
                .body("meta", hasKey("prevPage"))//В массиве 'meta' содержится поле 'prevPage'
                .body("meta.prevPage", instanceOf(String.class))//Тип данных в массиве 'meta' у параметра 'prevPage' является String
                .body("meta", hasKey("nextPage"))
                .body("meta.nextPage", instanceOf(String.class))//Тип данных в массиве 'meta' у параметра 'nextPage' является String
                .body("meta", hasKey("count"))
                .body("meta.count", anyOf(instanceOf(Integer.class)));//Тип данных в массиве 'meta' у параметра 'count' является Integer
    }
    @Test
    @DisplayName("Запрос с параметрами:\"order\"=\"ASC\", \"page\"= -1")
    void extendedNegativePageTest() {
        given()
                .header("X-Auth-Token", "4cab6cf7e5263d8c3e578ce38eee5cff")
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .queryParam("page", -1)
                .when()
                .get(baseURI)
                .then()
                .statusCode(404)//Проверка на код 404
                .contentType(ContentType.JSON)//В ответе приходит JSON
                .body("data", is(empty()));//Массив 'data' является пустым
    }
    @Test
    @DisplayName("Запрос с параметрами:\"order\"=\"ASC\", \"page\"= 0")
    void extendedZeroPageTest() {
        given()
                .header("X-Auth-Token", "4cab6cf7e5263d8c3e578ce38eee5cff")
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .queryParam("page", 0)
                .when()
                .get(baseURI)
                .then()
                .statusCode(200)//Проверка на код 200
                .contentType(ContentType.JSON)//В ответе приходит JSON
                .body("data", is(empty()));//Массив 'data' является пустым
    }
    @Test
    @DisplayName("Запрос с параметрами:\"order\"=\"ASC\", \"page\"= 40000")
    void extendedNonExistentPageTest() {
        given()
                .header("X-Auth-Token", "4cab6cf7e5263d8c3e578ce38eee5cff")
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .queryParam("page", 40000)
                .when()
                .get(baseURI)
                .then()
                .statusCode(200)//Проверка на код 200
                .contentType(ContentType.JSON)//В ответе приходит JSON
                .body("data", is(empty()));//Массив 'data' является пустым
    }
    @Test
    @DisplayName("Запрос без токена авторизации.")
    void smokeSeePostsNonAuthUserTest() {
        given()
                .header("X-Auth-Token", "")
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .queryParam("page", 1)
                .when()
                .get(baseURI)
                .then()
                .statusCode(401)//Проверка на код 401
                .contentType(ContentType.JSON);//В ответе приходит JSON
    }
}
