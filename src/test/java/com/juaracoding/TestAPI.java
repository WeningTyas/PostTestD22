package com.juaracoding;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import netscape.javascript.JSObject;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TestAPI {

    String myToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlNjdkNDIyOGM2YmE5YTA5NGM2MmE1OWZkMWVlZDc2ZSIsInN1YiI6IjY0ZGU1MTgyYjc3ZDRiMTE0MDE5Y2EwOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.GHxYYdr-CNQtaTRJVIGXZPF7VVZn7HfIjEWP72UK12U";
    String apiKey = "e67d4228c6ba9a094c62a59fd1eed76e";
    String baseURL = "https://api.themoviedb.org/3/movie";
    String movie_id = "615656";

    String movieNowPlaying = baseURL + "/now_playing";
    String moviePopular = baseURL + "/popular";
    String addRating = baseURL + "/" + movie_id + "/rating";


    @Test
    public void testMovieNowPlaying() {
        RestAssured.baseURI = baseURL;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest
                .queryParam("page", 1)
                .header("Authorization", myToken)
                .get("/now_playing");
        ResponseBody body = response.getBody();

        System.out.println("Response Body is: " + body.asString());
        JsonPath jsonPathEvaluator = response.jsonPath();
        String page = jsonPathEvaluator.getString("page");
        System.out.println(page);
    }

    @Test
    public void testMoviePopular() {
        RestAssured.baseURI = baseURL;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest
                .queryParam("page", 1)
                .header("Authorization", myToken)
                .get("/popular");
        ResponseBody body = response.getBody();

        System.out.println("Response Body is: " + body.asString());
        JsonPath jsonPathEvaluator = response.jsonPath();
        String page = jsonPathEvaluator.getString("page");
        System.out.println(page);
    }

    @Test
    public void testAddRating() {
        JSONObject request = new JSONObject();
        request.put("value", 4.50);
        //System.out.println(request.toJSONString());
        given()
                .header("Authorization", myToken)
                .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .post(addRating)
                .then()
                .statusCode(201)
                .log().body();
    }

}



/*
* Endpoint the movie db get movie
* - now playing,
* - get movie popular dan
* - post movie rating:

* Buatlah automation testing API pada endpoint diatas
* dengan method action serta gunakan validasi test
* */
