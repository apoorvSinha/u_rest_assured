package e2eApiEcom;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;


public class TestCls {
    public static String token = null;
    public static File f = null;
    public static String userId = null;
    public static String productId = null;
    public static String orderId = null;
    public JsonPath js = null;

    @BeforeTest
    public void setup(){
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"userEmail\": \"apoorv.linux.mail@gmail.com\",\n" +
                        "    \"userPassword\": \"JUSTdance@96\"\n" +
                        "}")
                .when().log().all()
                .post("api/ecom/auth/login")
                .then().assertThat().statusCode(200)
                .extract().response().asString();
        js = new JsonPath(response);
        token = js.getString("token");
        userId = js.getString("userId");
        System.out.println(userId);
    }

    @Test(priority = 1, groups = {"first_run"})
    public void createProduct() throws FileNotFoundException {
        f = new File("/home/apoorv/.config/variety/Downloaded/nasa_apod/AuroraIceland_Necchi_1280.jpg");
        String response = given().log().all()
                .header("Authorization", token)
                .multiPart("productName", "qwerty")
                .multiPart("productAddedBy", userId)
                .multiPart("productCategory", "fashion")
                .multiPart("productSubCategory", "shirts")
                .multiPart("productPrice", "11500")
                .multiPart("productDescription", "Stars Originals")
                .multiPart("productFor","women")
                .multiPart("productImage", f)
                .when().log().all()
                .post("api/ecom/product/add-product")
                .then().assertThat().statusCode(201)
                .extract().response().asString();
        js = new JsonPath(response);
        productId = js.getString("productId");
        Assert.assertEquals(js.getString("message"), "Product Added Successfully");
        System.out.println(productId);
    }

    @Test(priority = 2, groups = {"second_run"})
    public void createOrder(){
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .body("{\n" +
                        "    \"orders\": [\n" +
                        "        {\n" +
                        "            \"country\": \"India\",\n" +
                        "            \"productOrderedId\": \""+productId+"\"\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}")
                .when().log().all()
                .post("api/ecom/order/create-order")
                .then().assertThat().statusCode(201)
                .extract().response().asString();

        js = new JsonPath(response);
        Assert.assertEquals(js.getString("message"), "Order Placed Successfully");
        orderId = js.getString("orders");
        System.out.println(orderId);
    }
}
