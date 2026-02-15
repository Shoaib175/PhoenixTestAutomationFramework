package com.api.tests;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.lessThan;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;
import static com.api.utils.ConfigManager.*;

import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginAPITest {
	
	@Test(groups= {"smoke"})
	public void loginAPITest() throws IOException{
		
		UserCredentials creds = new UserCredentials("iamfd", "password");

		
		given()
		.baseUri(getProperty("BASE_URI"))
		.and()
		.contentType(JSON) // Helper method
		.accept(JSON)
		.log().uri()
		.and()
		.body(creds)
		.when()
		.post("login")
		.then()
		.log().body()
		.statusCode(200)
		.and()
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/loginResponseSchema.json"))
		.time(lessThan(1000L));
		
	}
	

}
