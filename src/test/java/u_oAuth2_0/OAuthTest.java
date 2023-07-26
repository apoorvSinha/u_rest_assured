package u_oAuth2_0;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static io.restassured.RestAssured.*;

public class OAuthTest {
    static String end_point = "https://rahulshettyacademy.com/getCourse.php";
    static String client_ID = "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com";
    static String client_Secret = "erZOWM9g3UtwNRj340YYaK_W";
    static WebDriver driver;

    public static void main(String[] args) {
//        OAuthTest ob = new OAuthTest();

        //rest-assured by default performs encoding

        // for authorization code "code" will be fetched with an extra level of security which has been cited in getCode method
        // for client Credentials -  code , redirect uri is not required

        String accessTokenJson = given().log().all().urlEncodingEnabled(false)
                .queryParam("code", "4%2F0AZEOvhVAZL4LxFkqUL4XV9BeAqsh4NEpqMhjPA8Jdjdh72r15F9FMQEngDhWaAf-6nXWdA")
                .queryParam("client_id", client_ID)
                .queryParam("client_secret", client_Secret)
                .queryParam("redirect_uri", end_point)
                .queryParam("grant_type", "authorization_code")
                .when().log().all()
                .post("https://www.googleapis.com/oauth2/v4/token").asString();

        JsonPath js = new JsonPath(accessTokenJson);
        String access_token = js.getString("access_token");


        // access token utilisation
        String response = given().queryParam("access_token", access_token).log().all()
                .when()
                .get(end_point)
                .asString();
        System.out.println(response);
    }
    /*
    public String getCode(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyjdss");
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("apoorv.work.amazon");
        driver.findElement(By.xpath("//*[@id=\"identifierNext\"]/div/button")).click();
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Luckysep@2022");
        driver.findElement(By.xpath("//*[@id=\"passwordNext\"]/div/button")).click();
        return driver.getCurrentUrl().split("code=")[1].split("&scope")[0];
    }
     */
}
