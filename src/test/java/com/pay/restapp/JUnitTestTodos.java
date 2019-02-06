package com.pay.restapp;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;



public class JUnitTestTodos {
	
	@BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 7070;
    }

	@Test
	public void testGetAllTodos() {
		get("/paypal/restapp/todos")
		.then()
		.assertThat()
		.body("$", hasSize(greaterThan(0)));
	}
	
	@Test
	public void testRetrieveTodo_StatusCode() {
		get("/paypal/restapp/todos/1")
		.then()
		.assertThat()
		.body("id", equalTo(1));
	}
	
	@Test
	public void testRetrieveTodo() {
		get("/paypal/restapp/todos/1")
		.then()
		.assertThat()
		.body("id", equalTo(1));
	}
	
	@Test
	public void testDeleteTodo_StatusCode() {
		delete("/paypal/restapp/todos/2")
		.then()
		.assertThat()
		.statusCode(200);
	}
	
}
