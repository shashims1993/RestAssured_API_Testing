package loggingexample;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;

public class ResponseLoggingExample {
	
	String consumerKey = "Xaf3qCeBDKTg8Q57jqub2bdpQ";
	String consumerSecret = "i68HcDppio8ITDO4at6xFPDGNIDuCJ1Ula7DARkzhFKobXpVdv";
	String accessToken = "990527525347577856-UNhrgWdoawSmuIUdpqQ6NxDo7fBSglq";
	String accessSecret = "6A84vx4Fe7Sak0LPrpkXJVfKpukBIkZSkz3ZYj1ztBUgD";
	
	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://api.twitter.com";
		RestAssured.basePath = "/1.1/statuses";
	}
	
	@Test
	public void testMethod() {
		given()
			.auth()
			.oauth(consumerKey, consumerSecret, accessToken, accessSecret)
			.queryParam("status", "My First Tweet 12")
		.when()
			.post("/update.json")
		.then()
			.log()
			//.headers()
			//.body()
			//.all()
			.ifError()
			.statusCode(200);
	}
}