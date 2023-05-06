package u_rest_assured;

import files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class ComplexJsonParse {
    public static void main(String[] args) {
        JsonPath js = new JsonPath(payload.coursePrice());
        int count = js.getInt("courses.size()");
        System.out.println(count);
        int totalAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(totalAmount);
        String title = js.getString("courses[0].title");
        System.out.println("First title is: " + title);

        String Ltitle = js.getString("courses[3].title");
        System.out.println("Last title is " + Ltitle);

        for (int i = 0; i < js.getInt("courses.size()"); i++) {
            String courseTitle = js.getString("courses[" + i + "].title");
//            System.out.println(js.getInt("courses[" + i + "].price"));
            if (courseTitle.equalsIgnoreCase("appium")) {
                String copiesAppium = js.getString("courses[" + i + "].copies");
                System.out.println("Appium copies sold are " + copiesAppium);
                break;
            }
        }

        //check price filter

    }


}
