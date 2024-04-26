package br.sp.jessie.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import io.restassured.RestAssured;

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
			.get("http://192.168.15.5:8001/tasks-backend/todo")
		.then()
			.statusCode(200)
		;
	}

	@Test
	public void deveAdicionarTarefaComSucesso() {
		RestAssured.given()
			.body("{ \"task\": \"Teste via API\", \"dueDate\": \"2024-12-26\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("http://192.168.15.5:8001/tasks-backend/todo")
		.then()
			.log().all()
			.statusCode(201)
		;
	
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
			.body("message", CoreMatchers.is("Due date must not be in past"))
		;
	
}
}
