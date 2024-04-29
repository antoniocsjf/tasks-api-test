package br.sp.jessie.tasks.apitest;

import org.junit.BeforeClass;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;

public class APITest {
    
    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://192.168.15.5:8001/tasks-backend";
    }
    
    @Test
    public void deveRetornarTarefas() {
        RestAssured.given()
            .log().all()
        .when()
            .get("/todo")
        .then()
            .statusCode(200);
    }

    @Test
    public void deveAdicionarTarefaComSucesso() {
        RestAssured.given()
            .body("{ \"task\": \"Teste via API\", \"dueDate\": \"2024-12-26\" }")
            .contentType(ContentType.JSON)
        .when()
            .post("/todo")
        .then()
            .log().all()
            .statusCode(201);
    }

    @Test
    public void naoDeveAdicionarTarefaInvalida() {
        RestAssured.given()
            .body("{ \"task\": \"Teste via API\", \"dueDate\": \"2020-12-26\" }")
            .contentType(ContentType.JSON)
        .when()
            .post("/todo")
        .then()
            .log().all()
            .statusCode(400)
            .body("message", CoreMatchers.is("Due date must not be in past"));
    }
}