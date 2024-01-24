package theCats;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.logging.FileHandler;

import static io.restassured.RestAssured.given;

public class getBreeds {
    static JsonPath js;
    public static void main(String[] args) throws IOException {
        RestAssured.baseURI = "https://api.thecatapi.com/";
        StringBuffer str = new StringBuffer();
        for(String string: getImages()){
            str.append(string +  "\n");
        }
        new getBreeds().writeFiles(String.valueOf(str));
    }


    private String getKey() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("./src/test/java/theCats/env.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            return reader.readLine().split("=")[1];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void writeFiles(String str) throws IOException {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("./src/test/java/theCats/abc.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writer.write(str);
        writer.close();
    }

    public static String[] getImages(){
        String response = given().log().all().header("x-api-key", new getBreeds().getKey())
                .header("Content-Type", "application/json")
                .queryParam("limit", 10)
                .when().get("v1/images/search")
                .then().log().all().assertThat().statusCode(200).extract().asString();
        js = new JsonPath(response);
        String catUrls = js.getString("url");
        catUrls = catUrls.split("\\[")[1];
        catUrls = catUrls.split("]")[0];
        return catUrls.split(",");
    }
}
