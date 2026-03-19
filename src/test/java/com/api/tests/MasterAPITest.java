package com.api.tests;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static com.api.constants.Role.*;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;

import static io.restassured.RestAssured.*;

public class MasterAPITest {
	
	@Test
	public void masterAPITest() throws IOException {
		given()
		.baseUri(getProperty("BASE_URI"))
		.and()
		.header("Authorization", getToken(FD))
		.and()
		.contentType("application/json")
		.log().all()
		.when()
		.post("/master")
		.then()
		.log().all()
		.statusCode(200)
		.body("message", equalTo("Success"))
		.time(lessThan(1000L))
		.body("data", notNullValue())
		.body("data", hasKey("mst_oem"))
		.body("data", hasKey("mst_model"))
		.body("$", hasKey("message"))//$ is used to refer to the root of the JSON response, so it checks if the root object contains the key "message"
		.body("data.mst_oem.size()", greaterThan(0))
		.body("data.mst_model.size()", greaterThan(0))
		.body("data.mst_oem.id",everyItem(notNullValue()))
		.body("data.mst_oem.name", everyItem(not(blankOrNullString())))
		.body(matchesJsonSchemaInClasspath("response-schema/masterAPIResponseSchema.json"));
	}
	
	@Test
	public void invalidTokenMasterAPITest() throws IOException {
		given()
		.baseUri(getProperty("BASE_URI"))
		.and()
		.header("Authorization","")
		.and()
		.contentType("application/json")
		.log().all()
		.when()
		.post("/master")
		.then()
		.log().all()
		.statusCode(401);
	}

}
