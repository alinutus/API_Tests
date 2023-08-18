package pojo;

import lombok.Getter;

@Getter
public class ListsResults {
    String description;
    int favorite_count;
    int id;
    int item_count;
    String iso_639_1;
    String list_type;
    String name;
    String poster_path;

    @Override
    public String toString() {
        return "{" +
                "\n description = " + description +
                ", \n favorite_count = " + favorite_count +
                ", \n id = " + id +
                ", \n item_count = " + item_count +
                ", \n iso_639_1 = " + iso_639_1 +
                ", \n list_type = " + list_type +
                ", \n name = " + name +
                ", \n poster_path = " + poster_path +
                " \n }\n";
    }
}
