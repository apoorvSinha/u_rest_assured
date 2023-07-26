package pojo;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.WebDriver;

import static io.restassured.RestAssured.given;

public class OAuth {
    static String end_point = "https://rahulshettyacademy.com/getCourse.php";
    static String client_ID = "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com";
    static String client_Secret = "erZOWM9g3UtwNRj340YYaK_W";
    static WebDriver driver;

    public static void main(String[] args) {
//        OAuthTest ob = new OAuthTest();


        /*
        https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyjdss
        * */

        //rest-assured by default performs encoding

        // for authorization code "code" will be fetched with an extra level of security which has been cited in getCode method
        // for client Credentials -  code , redirect uri is not required

        String accessTokenJson = given().log().all().urlEncodingEnabled(false)
                .queryParam("code", "4%2F0AZEOvhW4IUFDQXRvUd0VU07GO5F6s1wTGjP-Zp6qR5MOM2orLMOui1_CkXPFX-SfzNt1vQ")
                .queryParam("client_id", client_ID)
                .queryParam("client_secret", client_Secret)
                .queryParam("redirect_uri", end_point)
                .queryParam("grant_type", "authorization_code")
                .when().log().all()
                .post("https://www.googleapis.com/oauth2/v4/token").asString();

        JsonPath js = new JsonPath(accessTokenJson);
        String access_token = js.getString("access_token");


        // access token utilisation
        GetCourses gc  = given().queryParam("access_token", access_token)
                .log().all()
                .expect().defaultParser(Parser.JSON)        // rest assured will treat the data as Json
                .when()
                .get(end_point)
                .as(GetCourses.class);

        System.out.println(gc.getLinkedIn());
        System.out.println(gc.getInstructor());
        System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
        int apiCourses = gc.getCourses().getApi().size();
        for(int i=0 ; i< apiCourses; i++){
            if(gc.getCourses().getApi().get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
                System.out.println(gc.getCourses().getApi().get(1).getPrice());
            }
        }

        int webAutomationCourses = gc.getCourses().getWebAutomation().size();
        for(int i=0; i< webAutomationCourses; i++){
            System.out.println(gc.getCourses().getWebAutomation().get(i).getCourseTitle());
        }

    }

}
