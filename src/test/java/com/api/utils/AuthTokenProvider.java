package com.api.utils;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import static org.hamcrest.Matchers.*;

import com.api.constants.Role;
import com.api.pojo.UserCredentials;

import io.restassured.http.ContentType;

public class AuthTokenProvider {
	
	
	private AuthTokenProvider() {
		
	}
	
	public static String getToken(Role role) throws IOException {
		
		
		UserCredentials userCreds = null;
		switch(role) {
		case FD -> userCreds = new UserCredentials("iamfd", "password"); // arrow operator is a part of Java 14
		case SUP -> userCreds = new UserCredentials("iamsup", "password");
		case ENG -> userCreds = new UserCredentials("iameng", "password");
		case QC -> userCreds = new UserCredentials("iamqc", "password");
		}
		
		
		String token = 
		
		given().
		baseUri(ConfigManager.getProperty("BASE_URI"))
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.body(userCreds)
		
		.when()
		.post("/login")
		
		.then()
		.log().ifValidationFails()
		.statusCode(200)
		.body("message",equalTo("Success"))
		.extract()
		.body()
		.jsonPath()
		.getString("data.token");
		
		
		System.out.println(token);
		return token;
		
	}

}
