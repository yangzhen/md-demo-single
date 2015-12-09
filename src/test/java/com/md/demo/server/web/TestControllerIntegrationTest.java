package com.md.demo.server.web;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;

public class TestControllerIntegrationTest {

	@Before
	public void init() {
		RestAssured.registerParser("text/text", Parser.JSON);
		RestAssured.registerParser("text/json", Parser.JSON);
	}

	@Test
	public void testGetDemo() {
		int id = 23;
		String text = "do";
		given().port(8081).
		param("id", id).
		param("text", text).
		when().
		get("/md-demo-single/getDemo").
		then().log().all().
		body("result", is(true)).
		body("data.id", is(id)).
		body("data.text", is(text));
	}

}
