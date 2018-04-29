package assertexamples;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;

public class TwitterSoftAssertExample {
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
	public void readTweets() {
		given()
			.auth()
			.oauth(consumerKey, consumerSecret, accessToken, accessSecret)
			.queryParam("user_id", "apiautomation")
		.when()
			.get("/user_timeline.json")
		.then()
			.statusCode(200)
			.body("user.name", hasItem("RestAPI Automation"))
			.body("entities.hashtags[0].text", hasItem("multiple1"),
				  "entities.hashtags[0].size()", equalTo(3),
				  "entities.hashtags[1].size()", lessThan(2));
	}
}