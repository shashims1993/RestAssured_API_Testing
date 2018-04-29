package twitterapexample;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TwitterExtractResponse {
	

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
	public void extractResponse() {
		Response response =
			given()
				.auth()
				.oauth(consumerKey, consumerSecret, accessToken, accessSecret)
				.queryParam("status", "My First Tweetss")
			.when()
				.post("/update.json")
			.then()
				.statusCode(200)
				.extract()
				.response();
		
		String id = response.path("id_str");
		System.out.println("The response.path: " + id);

		String responseString = response.asString();
		System.out.println(responseString);

		JsonPath jsPath = new JsonPath(responseString);
		String name = jsPath.get("user.name");
		System.out.println("The username is: " + name);
	}
}