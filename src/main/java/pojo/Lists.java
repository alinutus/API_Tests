package pojo;

import lombok.Getter;
import pojo.ListsResults;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Lists {
    int id;
    int page;
    List<ListsResults> results;
    int total_pages;
    int total_results;

    @Override
    public String toString() {
        return "Lists[ " +
                "\n page = " + page +
                ", \n results: \n" + results +
                ", \n total_pages = " + total_pages +
                ", \n total_results = " + total_results +
                " \n ]";
    }
}
