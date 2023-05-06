package u_rest_assured;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class DynamicJson {

    @Test
    public void addBook(){
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().header("Content-Type", "application/json")
                .body(payload.addBook())
                .when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath js =  ReusableMethods.rawToJson(response);
        String id = js.getString("ID");
        System.out.println(id);

    }
}
