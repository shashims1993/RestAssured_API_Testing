package twitterapexample;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TwitterEndToEndWorkflow {

	String consumerKey = "Xaf3qCeBDKTg8Q57jqub2bdpQ";
	String consumerSecret = "i68HcDppio8ITDO4at6xFPDGNIDuCJ1Ula7DARkzhFKobXpVdv";
	String accessToken = "990527525347577856-UNhrgWdoawSmuIUdpqQ6NxDo7fBSglq";
	String accessSecret = "6A84vx4Fe7Sak0LPrpkXJVfKpukBIkZSkz3ZYj1ztBUgD";
	String tweetId = "";

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://api.twitter.com";
		RestAssured.basePath = "/1.1/statuses";
	}

	@Test
	public void postTweet() {
		Response response =
			given()
				.auth()
				.oauth(consumerKey, consumerSecret, accessToken, accessSecret)
				.queryParam("status", "My First Tweetssss")
			.when()
				.post("/update.json")
			.then()
				.statusCode(200)
				.extract()
				.response();
		
		tweetId = response.path("id_str");
		System.out.println("The response.path: " + tweetId);
	}
	
	@Test(dependsOnMethods={"postTweet"})
	public void readTweet() {
		Response response =
			given()
				.auth()
				.oauth(consumerKey, consumerSecret, accessToken, accessSecret)
				.queryParam("id", tweetId)
			.when()
				.get("/show.json")
			.then()
				.extract()
				.response();
		
		String text = response.path("text");
		System.out.println("The tweeted text is: " + text);
	}

	@Test(dependsOnMethods={"readTweet"})
	public void deleteTweet() {
			given()
				.auth()
				.oauth(consumerKey, consumerSecret, accessToken, accessSecret)
				.pathParam("id", tweetId)
			.when()
				.post("/destroy/{id}.json")
			.then()
				.statusCode(200);
	}
}