package parser;

import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.codehaus.jackson.map.ObjectMapper;
import pojo.Lists;
import pojo.Rating;

public class JsonParser {
    @SneakyThrows
    public static Lists readListsResponse(Response jsonResponse) {
        return new ObjectMapper().readValue(parseJsonResponseToString(jsonResponse), Lists.class);
    }

    @SneakyThrows
    public static Rating readRatingResponse(Response jsonResponse) {
        return new ObjectMapper().readValue(parseJsonResponseToString(jsonResponse), Rating.class);
    }

    public static String parseJsonResponseToString(Response jsonResponse){
        return jsonResponse.body().asString();
    }
}
