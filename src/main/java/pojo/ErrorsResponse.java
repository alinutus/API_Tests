package pojo;

import lombok.Getter;

import java.util.List;

@Getter
public class ErrorsResponse {
    List errors;
    Boolean success;

    @Override
    public String toString() {
        return "RESPONSE [" +
                "\n success = " + success +
                ", \n errors = " + errors +
                " \n ]";
    }
}
