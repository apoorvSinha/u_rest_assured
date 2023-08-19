package e2eApiEcomSerialized;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EcommerceApiTest {
    public static void main(String[] args) {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("apoorv.linux.mail@gmail.com");
        loginRequest.setUserPassword("JUSTdance@96");

        RequestSpecification reqLogin = given().relaxedHTTPSValidation().log().all().spec(requestSpecification).body(loginRequest);

        LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login").then().log().all().extract().response().as(LoginResponse.class);
        System.out.println(loginResponse.getToken());
        System.out.println(loginResponse.getUserId());

        String token = loginResponse.getToken();
        String userId = loginResponse.getUserId();

        // Add Product
        RequestSpecification addProductBase = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).build();

        RequestSpecification reqAddProduct = given().relaxedHTTPSValidation().log().all().spec(addProductBase)
                .param("productName", "Payjama")
                .param("productAddedBy", userId)
                .param("productCategory", "fashion")
                .param("productSubCategory","shirts")
                .param("productPrice","158")
                .param("productDescription", "Puma")
                .param("productFor", "women")
                .multiPart("productImage", new File("/home/apoorv/.config/variety/Downloaded/nasa_apod/AuroraIceland_Necchi_1280.jpg"));

        AddedProduct addedProduct = reqAddProduct.when().post("/api/ecom/product/add-product")
                .then().log().all().extract().response().as(AddedProduct.class);
        String productId = addedProduct.getProductId();
        System.out.println(addedProduct.getProductId());

        // Create Order
        RequestSpecification createOrderBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token)
                .setContentType(ContentType.JSON).build();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCountry("India");
        orderDetail.setProductOrderedId(addedProduct.getProductId());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);
        Orders orders = new Orders();
        orders.setOrders(orderDetailList);

        RequestSpecification reqCreateOrder = given().relaxedHTTPSValidation().log().all().spec(createOrderBase)
                .body(orders);

        String responseCreateOrder = reqCreateOrder.when().post("/api/ecom/order/create-order")
                .then().log().all().extract().response().asString();
        System.out.println(responseCreateOrder);


        // Delete Product
        RequestSpecification deleteOrder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", token)
                .setContentType(ContentType.JSON).build();
        RequestSpecification reqDeleteOrder = given().log().all().spec(deleteOrder).pathParams("productId", productId);

        String deletedConfirmation = reqDeleteOrder.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asString();

        System.out.println(deletedConfirmation);

    }
}