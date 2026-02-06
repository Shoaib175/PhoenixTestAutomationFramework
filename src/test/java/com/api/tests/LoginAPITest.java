package com.api.tests;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.lessThan;


import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;

import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginAPITest {
	
	@Test(groups= {"smoke"})
	public void loginAPITest(){
		
		UserCredentials creds = new UserCredentials("iamfd", "password");
		
		given()
		.baseUri("http://64.227.160.186:9000/v1")
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
