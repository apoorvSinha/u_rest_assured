package u_rest_assured;


import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class DynamicJson {
    ArrayList<String> al = new ArrayList<String>();

    @Test(dataProvider = "bookData", priority = 1)
    public void addBook(String isbn, String aisle) {
        RestAssured.baseURI = "http://216.10.245.166" ;
        String response = given().header("Content-Type", "application/json")
//                .body(payload.addBook("c876cd", "16qwetgr"))
                .body(payload.addBook(isbn, aisle))
                .when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        System.out.println(response);
        JsonPath js = ReusableMethods.rawToJson(response);
        String id = js.getString("ID");
        al.add(id);
        System.out.println(id);
        System.out.println("ID list is "+ Arrays.toString(al.toArray()));

    }

    @Test(priority = 2)
    public void deleteBook() {
        RestAssured.baseURI = "http://216.10.245.166" ;
        for (String s : al) {
            given().header("Content-Type", "application/json")
                    .body(payload.deleteBook(s))
                    .when().post("Library/DeleteBook.php")
                    .then().log().all().assertThat().statusCode(200)
                    .body("msg", equalTo("book is successfully deleted"));
        }
    }


    @DataProvider(name = "bookData")
    public Object[][] myData() {
        return new Object[][]{{"his12", "32thr"}, {"his13", "33thr"}, {"his14", "34thr"}};
    }
}
