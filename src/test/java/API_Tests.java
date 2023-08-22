import io.qameta.allure.Description;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import parser.JsonParser;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
public class API_Tests {
    @BeforeClass
    public static void setup()
    {
        RestAssured.baseURI = "https://api.themoviedb.org/";
        RestAssured.authentication = oauth2("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkM2YwNDU4MTY1YzJiMjk1ZjhhNGJkNzAzZGI3OTAyNyIsInN1YiI6IjY0ZDlkZDBjMDAxYmJkMDBjNmM4MzBmNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.fJzaosXuEDe18UwgDwmdFZIMXp3ZNwvufC-VypprkCA");
    }

    @BeforeTest
    public void logsToAllure(){
        RestAssured.filters(new AllureRestAssured());
    }

    @Test
    @Description("GET Movie Lists, expected response with Status 200")
    public static void getListStatus200(){
        Response response = given()
                .when()
                .get("3/movie/569094/lists")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("page", equalTo(1))
                .extract()
                .response();
        System.out.println(JsonParser.readListsResponse(response));
        log.info("GET Movie Lists, Status 200" + response);
    }

    @Test
    @Description("GET Collection With Nonexistent CollectionId, expected response with Status 404")
    public static void getCollectionStatus404(){
        Response response = given()
                .when()
                .get("3/collection/collection_id")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("status_message", equalTo("The resource you requested could not be found."))
                .extract()
                .response();
        System.out.println(JsonParser.readResponse(response));
        log.info("GET Collection, Status 404" + response);
    }

    @Test
    @Description("GET Movie With Nonexistent MovieId, expected response with Status 422")
    public static void getMovieStatus422(){
        Response response = given().queryParam("page", "4454545")
                .when()
                .get("3/movie/now_playing")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("errors", contains("page must be less than or equal to 500"))
                .extract()
                .response();
        System.out.println(JsonParser.readResponseWithErrors(response));
        log.info("GET Movie, Status 422" + response);
    }

    @Test
    @Description("POST Add Rating To Movie, expected response with Status 200")
    public static void postAddRatingToMovieStatus200(){
        Response response = given().body("{\"value\":\"7\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("3/movie/976573/rating")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("status_message", equalTo("The item/record was updated successfully."))
                .extract()
                .response();
        System.out.println(JsonParser.readResponse(response));
        log.info("POST Add Rating To Movie, Status 200" + response);
    }

    @Test
    @Description("POST Token With Nonexistent Login, expected response with Status 400")
    public static void postCreateTokenStatus400(){
        Response response = given().queryParam("RAW_BODY", "")
                .when()
                .post("3/authentication/token/validate_with_login")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("status_message", equalTo("Invalid parameters: Your request parameters are incorrect."))
                .extract()
                .response();
        System.out.println(JsonParser.readResponse(response));
        log.info("POST Token, Status 400" + response);
    }

    @Test
    @Description("POST Session With Empty Body, expected response with Status 404")
    public static void postCreateSessionStatus404(){
        Response response = given().queryParam("RAW_BODY", "")
                .when()
                .post("3/authentication/session/new")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("status_message", equalTo("The resource you requested could not be found."))
                .extract()
                .response();
        System.out.println(JsonParser.readResponse(response));
        log.info("GET Session, Status 404" + response);
    }

    @Test
    @Description("DELETE Movie Rating, expected response with Status 200")
    public static void deleteRatingStatus200(){
        Response response = given()
                .when()
                .contentType(ContentType.JSON)
                .delete("/3/movie/569094/rating")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("status_message", equalTo("The item/record was deleted successfully."))
                .extract()
                .response();
        System.out.println(JsonParser.readResponse(response));
        log.info("DELETE Rating, Status 200" + response);
    }

    @Test
    @Description("DELETE Nonexistent Movie Rating, expected response with Status 404")
    public static void deleteRatingStatus404(){
        Response response = given()
                .when()
                .contentType(ContentType.JSON)
                .delete("/3/movie/569/rating")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("status_message", equalTo("The resource you requested could not be found."))
                .extract()
                .response();
        System.out.println(JsonParser.readResponse(response));
        log.info("DELETE Rating, Status 404" + response);
    }

    @Test
    @Description("DELETE Movie Rating With Nonexistent SessionId, expected response with Status 401")
    public static void deleteRatingStatus401(){
        Response response = given().queryParam("session_id", "35353")
                .when()
                .contentType(ContentType.JSON)
                .delete("/3/movie/5690/rating")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("status_message", equalTo("Authentication failed: You do not have permissions to access the service."))
                .extract()
                .response();
        System.out.println(JsonParser.readResponse(response));
        log.info("DELETE Rating, Status 401" + response);
    }
}
