package theCats;

import io.restassured.RestAssured;

import java.io.*;
import java.util.logging.FileHandler;

import static io.restassured.RestAssured.given;

public class getBreeds {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://api.thecatapi.com/";
        getImages();
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

    private void writeFiles(String str){
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("./src/test/java/theCats/abc.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            writer.write(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getImages(){
        given().log().all().header("x-api-key", new getBreeds().getKey())
                .header("Content-Type", "application/json")
                .queryParam("limit", 3)
                .when().get("v1/images/search")
                .then().log().all().assertThat().statusCode(200);
    }
}
