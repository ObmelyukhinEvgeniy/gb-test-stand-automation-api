package com.geekbrains;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class MyPostsApiTest {
    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://test-stand.gb.ru/api/posts";//Устанавливаем значение урла
    }

    @Test
    void smokeOwnerPostsTest() {
        given()
                .header("X-Auth-Token", "10fb5777797ab0ec12cd5ce6ed7762eb")
                .queryParam("owner", "notMe")//Устанавливаем query параметры ключ/значение
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .queryParam("page", 1)
                .log().all()
                .when()
                .get("/api/posts")//Устанавливаем домен после урла
                .then()
                .statusCode(200)//Проверка на код 200
                .time(Matchers.lessThanOrEqualTo(2000L))//Проверка на время ответа не превысит 3сек
                .log().all();
    }
}
