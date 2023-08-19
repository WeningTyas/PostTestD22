package com.juaracoding;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestTMDB {

    String baseURL = "https://api.themoviedb.org/3/movie";
    String myToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlNjdkNDIyOGM2YmE5YTA5NGM2MmE1OWZkMWVlZDc2ZSIsInN1YiI6IjY0ZGU1MTgyYjc3ZDRiMTE0MDE5Y2EwOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.GHxYYdr-CNQtaTRJVIGXZPF7VVZn7HfIjEWP72UK12U";
    String apiKey  = "e67d4228c6ba9a094c62a59fd1eed76e";

    String movie_id = "615656";

    String movieNowPlaying = baseURL + "/now_playing";
    String moviePopular = baseURL + "/popular";
    String addRating = baseURL + "/" + movie_id + "/rating";

    // ====================== Now Playing ========================== //
    @Test
    public void testMovieNowPlaying(){
        //Status Code => 200
        RequestSpecification request = RestAssured.given();
        Response response = request
                .header("Authorization", myToken)
                .get(movieNowPlaying);
        int status = response.getStatusCode();

        Assert.assertEquals(status,200);
        // Note:
        // header harus dipanggil duluan daripada get URLnya
    }
    @Test
    public void testIdPlaying() {
        //Data di id pertama == 667538
        given()
                .header("Authorization", myToken)
                .get(movieNowPlaying)
                .then()
                .statusCode(200)
                .body("results.id[1]",  equalTo(667538));
    }

    // ====================== Movie Popular ========================== //
    @Test
    public void testMoviePopular(){
        //Status Code => 200
        RequestSpecification request = RestAssured.given();
        Response response = request
                .header("Authorization", myToken)
                .get(moviePopular);
        int status = response.getStatusCode();

        Assert.assertEquals(status,200);
    }
    @Test
    public void testIdPopular() {
        //Data di id kedua == 667538
        given()
                .header("Authorization", myToken)
                .get(moviePopular)
                .then()
                .statusCode(200)
                .body("results.id[2]",  equalTo(667538));
    }

    // ====================== Add Rating ========================== //
    @Test
    public void testStatusAddRating() {
        JSONObject request = new JSONObject();
        request.put("value", 4.50);
        Response response = given()
                .header("Authorization", myToken)
                .header("Content-Type", "application/json")
                .body(request.toJSONString())
                .when()
                .post(addRating);
        int statusCode = response.getStatusCode();

        Assert.assertEquals(statusCode, 201);
    }
    @Test
    public void testAddRating() {
        //Data di Status Message == Sukses
        JSONObject request = new JSONObject();
        request.put("value", 4.50);
        given()
                .header("Authorization", myToken)
                .header("Content-Type", "application/json")
                .body(request.toJSONString())
                .when()
                .post(addRating)
                .then()
                .statusCode(201)
                .body("status_message",  equalTo("The item/record was updated successfully."));
    }
}
