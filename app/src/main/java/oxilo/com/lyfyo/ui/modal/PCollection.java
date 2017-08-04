package oxilo.com.lyfyo.ui.modal;

/**
 * Created by nikk on 20/7/17.
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "title"
})
public class PCollection {

    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String title;

    private ArrayList<Result> list;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Result> getList() {
        return list;
    }

    public void setList(ArrayList<Result> list) {
        this.list = list;
    }
}
