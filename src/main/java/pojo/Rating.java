package pojo;

import lombok.Getter;

@Getter
public class Rating {
    boolean success;
    int status_code;
    String status_message;

    @Override
    public String toString() {
        return "RESPONSE [" +
                "\n success = " + success +
                ", \n status_code = " + status_code +
                ", \n status_message = " + status_message +
                " \n ]";
    }
}
