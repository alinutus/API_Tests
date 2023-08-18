import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
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

    @Test
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
        log.info("GET Movie Lists Status 200");
    }

    @Test
    public static void getListStatus404(){
        given()
                .when()
                .get("3/collection/collection_id")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("status_message", equalTo("The resource you requested could not be found."))
                .extract()
                .response();
    }

    @Test
    public static void getListStatus422(){
        given().queryParam("page", "4454545")
                .when()
                .get("3/movie/now_playing")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("errors", contains("page must be less than or equal to 500"))
                .extract()
                .response();
    }

    @Test
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
        System.out.println(JsonParser.readRatingResponse(response));
    }

    @Test
    public static void postCreateSessionStatus400(){
        given().queryParam("RAW_BODY", "")
                .when()
                .post("3/authentication/token/validate_with_login")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("status_message", equalTo("Invalid parameters: Your request parameters are incorrect."))
                .extract()
                .response();
    }

    @Test
    public static void postCreateSessionStatus404(){
        given().queryParam("RAW_BODY", "")
                .when()
                .post("3/authentication/session/new")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("status_message", equalTo("The resource you requested could not be found."))
                .extract()
                .response();
    }

    @Test
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
        System.out.println(JsonParser.readRatingResponse(response));
    }

    @Test
    public static void deleteRatingStatus404(){
        given()
                .when()
                .contentType(ContentType.JSON)
                .delete("/3/movie/569/rating")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("status_message", equalTo("The resource you requested could not be found."))
                .extract()
                .response();
    }

    @Test
    public static void deleteRatingStatus401(){
        given().queryParam("session_id", "35353")
                .when()
                .contentType(ContentType.JSON)
                .delete("/3/movie/5690/rating")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("status_message", equalTo("Authentication failed: You do not have permissions to access the service."))
                .extract()
                .response();
    }
}
