package extractresponse;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class GoogleExtractResponseJSON {
	
	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://maps.googleapis.com";
		RestAssured.basePath = "/maps/api";
	}
	
	@Test
	public void extractResponse() {
		Response response = given()
			.queryParam("units", "imperial")
			.queryParam("origins", "Washington,DC")
			.queryParam("destinations", "New+York+City,NY")
			.queryParam("key", "AIzaSyAFNxOzcDNEZ9coJzPc_9N-CA8Euun2fDA")
		.when()
			.get("/distancematrix/json")
		.then()
			.statusCode(200).extract().response();
		
		String responseString = response.asString();
		System.out.println(responseString);
		System.out.println(response.path("rows.elements.duration.value"));
		String value = response.path("rows.elements.duration.value").toString();
		System.out.println("The duration value is: " + value);
		
		JsonPath jsonPath = new JsonPath(responseString);
		String text = jsonPath.get("rows.elements.duration.text").toString();
		System.out.println("The duration text using jsonPath is: " + text);
	}
}