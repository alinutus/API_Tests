package parser;

import lombok.SneakyThrows;
import org.codehaus.jackson.map.ObjectMapper;
import pojo.ErrorsResponse;
import pojo.Lists;
import pojo.Response;

public class JsonParser {
    @SneakyThrows
    public static Lists readListsResponse(io.restassured.response.Response jsonResponse) {
        return new ObjectMapper().readValue(parseJsonResponseToString(jsonResponse), Lists.class);
    }

    @SneakyThrows
    public static Response readResponse(io.restassured.response.Response jsonResponse) {
        return new ObjectMapper().readValue(parseJsonResponseToString(jsonResponse), Response.class);
    }

    @SneakyThrows
    public static ErrorsResponse readResponseWithErrors(io.restassured.response.Response jsonResponse) {
        return new ObjectMapper().readValue(parseJsonResponseToString(jsonResponse), ErrorsResponse.class);
    }

    public static String parseJsonResponseToString(io.restassured.response.Response jsonResponse){
        return jsonResponse.body().asString();
    }
}
