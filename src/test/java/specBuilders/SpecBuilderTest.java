package specBuilders;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import serialization.AddPlace;
import serialization.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {

    public static void main(String[] args) {
        AddPlace p = new AddPlace();
        p.setAccuracy(50);
        p.setAddress("29, side layout, cohen 09");
        p.setName("Frontline house");
        p.setPhone_number("(+91) 983 893 3937");
        p.setWebsite("http://google.com");
        p.setLanguage("French-IN");
        List<String> templis= new ArrayList<>();
        templis.add("shoe park");
        templis.add("shop");
        p.setTypes(templis);
        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        p.setLocation(location);



        RequestSpecification requestSpecification =  new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                        .setContentType(ContentType.JSON).build();
        RequestSpecification res = given().spec(requestSpecification).body(p);

        ResponseSpecification responseSpecBuilder =  new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        Response response = res.when().post("/maps/api/place/add/json")
                .then().log().all().spec(responseSpecBuilder).extract().response();

        String responseString = response.asString();
        System.out.println(responseString);

    }
}
