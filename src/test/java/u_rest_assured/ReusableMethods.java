package u_rest_assured;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {
    public static JsonPath rawToJson(String response){
        return new JsonPath(response);
    }
}
