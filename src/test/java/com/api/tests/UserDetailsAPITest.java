package com.api.tests;

import static com.api.utils.AuthTokenProvider.getToken;
import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.lessThan;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.constants.Role.*;

import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;


public class UserDetailsAPITest {
	
	
	@Test(groups= {"smoke"})
	public void userDetailsAPITest() throws IOException {
		
		Header authHeader= new Header("Authorization", getToken(QC));  // 
		
		given()
		.baseUri(getProperty("BASE_URI"))
		.header(authHeader)
		.and()
		.contentType(JSON)
		.accept(JSON)
		
		.log().uri()
		.log().method()
		.log().headers()
		
		.when()
		.get("userdetails")
		
		.then()
		.statusCode(200)
		.time(lessThan(1000L))
		.and()
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/userDetailsResponseSchema.json"))
		.log().body();
		
		
	}

}
