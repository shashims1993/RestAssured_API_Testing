package Request_Response_Specification;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.*;

import java.util.concurrent.TimeUnit;

public class SpecificationWithTime {
	String consumerKey = "Xaf3qCeBDKTg8Q57jqub2bdpQ";
	String consumerSecret = "i68HcDppio8ITDO4at6xFPDGNIDuCJ1Ula7DARkzhFKobXpVdv";
	String accessToken = "990527525347577856-UNhrgWdoawSmuIUdpqQ6NxDo7fBSglq";
	String accessSecret = "6A84vx4Fe7Sak0LPrpkXJVfKpukBIkZSkz3ZYj1ztBUgD";
	
	RequestSpecBuilder requestBuilder;
	static RequestSpecification requestSpec;
	ResponseSpecBuilder responseBuilder;
	static ResponseSpecification responseSpec;

	@BeforeClass
	public void setup() {
		AuthenticationScheme authScheme = 
				RestAssured.oauth(consumerKey, consumerSecret, accessToken, accessSecret);
		requestBuilder = new RequestSpecBuilder();
		requestBuilder.setBaseUri("https://api.twitter.com");
		requestBuilder.setBasePath("/1.1/statuses");
		requestBuilder.addQueryParam("user_id", "Shashikant");
		requestBuilder.setAuth(authScheme);
		requestSpec = requestBuilder.build();
		
		responseBuilder = new ResponseSpecBuilder();
		responseBuilder.expectStatusCode(200);
		responseBuilder.expectResponseTime(lessThan(3L), TimeUnit.SECONDS);
		responseBuilder.expectBody("user.name", hasItem("Shashikant"));
		responseSpec = responseBuilder.build();
	}

	@Test
	public void readTweets() {
		given()
			.spec(requestSpec)
		.when()
			.get("/user_timeline.json")
		.then()
		.log().all()
			.spec(responseSpec)
			.body("user.screen_name", hasItem("Shashik25985406"));
	}
}