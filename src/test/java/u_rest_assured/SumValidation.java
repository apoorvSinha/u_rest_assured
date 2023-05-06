package u_rest_assured;

import files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidation {

    @Test
    public void testAmount(){
        JsonPath js = new JsonPath(payload.coursePrice());
        int count = js.getInt("courses.size()");
        int sale = 0;
        for (int i = 0; i < count; i++) {
            sale += js.getInt("courses[" + i + "].price") * js.getInt("courses[" + i + "].copies");
        }
        Assert.assertEquals(sale, js.getInt("dashboard.purchaseAmount"));
    }
}
