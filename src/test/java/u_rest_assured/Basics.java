package u_rest_assured;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import files.payload;
import org.testng.Assert;

public class Basics {
    public static void main(String[] args) {
        // TODO - validate if add place works as expected
        /*
         * given- all input details
         * when - submit the API - resource, http methods
         * then - validate the response
         */

        // TODO Add place -> update place with new address -> get place to retrieve the place assert new address
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(payload.addPlace()).when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.52 (Ubuntu)").extract().asString();

        System.out.println(response);

        JsonPath js = new JsonPath(response);
        String place_id = js.getString("place_id");



        String updatedAddress = "105/334 Shiva Dham, Kailash";

        // TODO Put place -> update address
        System.out.println(place_id);
        given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(payload.putPlace(place_id, updatedAddress))
                .when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

        // TODO Get place -> Get updated address
        given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id)
                .when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).body("address", equalTo(updatedAddress));

        // TODO Get place -> Get updated address
        String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id)
                .when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
//        JsonPath js1 = new JsonPath(getPlaceResponse);
        JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);

        String actualAddress = js1.getString("address");
        Assert.assertEquals(updatedAddress, actualAddress);

    }
}
