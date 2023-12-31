package u_jira;

import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import java.io.File;

import static io.restassured.RestAssured.*;

public class JIraTest {
    static SessionFilter session = null;
    static JsonPath js = null;

    public static void main(String[] args) {
        baseURI = "http://localhost:7080/";
        session = new SessionFilter();        //shortcut instead of JsonPath
        JIraTest.logIn();
        JIraTest.addComment();
//        JIraTest.addAttachment();
        JIraTest.getRequest();
    }

    public static void logIn() {

        given().header("Content-Type", "application/json").log().all()
                .body("""
                        {
                            "username": "apoorv.work.amazon",
                            "password": "JUSTdance@102"
                        }
                        """)
                .filter(session)
                .when().post("rest/auth/1/session")
                .then().log().all().extract().response().asString();
    }


    public static void addComment() {
        String addCommentResponse = given().pathParams("id", "10202").log().all()
                .header("Content-Type", "application/json")
                .body("""
                        {
                            "body": "chat gpt is little jealous",
                            "visibility": {
                                "type": "role",
                                "value": "Administrators"
                            }
                        }
                        """)
                .filter(session)
                .when().post("/rest/api/2/issue/{id}/comment")     //will check at runtime for path parameter in curly brace4
                .then().log().all().assertThat().statusCode(201).extract().asString();
        js = new JsonPath(addCommentResponse);
        String commentId = js.getString("id");
    }

    public static void addAttachment() {
        given().header("X-Atlassian-Token", "no-check")
                .filter(session)
                .pathParams("ID", "10202")
                .header("Content-Type", "multipart/form-data")
                .multiPart("file", new File("./src/test/java/u_jira/sample.txt"))       //sending file
                .when().post("/rest/api/2/issue/{ID}/attachments")
                .then().log().all().assertThat().statusCode(200);

    }

    public static void getRequest() {
        String response = given().filter(session)
                .pathParams("ID", "10202").log().all()
                .queryParam("fields", "comment")
                .when()
                .get("/rest/api/2/issue/{ID}")
                .then().log().all().extract().asString();
        js = new JsonPath(response);
        int commentCount = js.getInt("fields.comment.comments.size()");
        for (int i = 0; i < commentCount; i++) {
            System.out.println(js.getInt("fields.comment.comments["+i+"].id"));
        }
    }


}
