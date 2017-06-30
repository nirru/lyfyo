package oxilo.com.lyfyo.ui.modal;

/**
 * Created by nikk on 29/6/17.
 */
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "L_id",
        "L_name",
        "L_city"
})
public class LocationModal {
    @JsonProperty("L_id")
    private String lId;
    @JsonProperty("L_name")
    private String lName;
    @JsonProperty("L_city")
    private String lCity;

    @JsonProperty("L_id")
    public String getLId() {
        return lId;
    }

    @JsonProperty("L_id")
    public void setLId(String lId) {
        this.lId = lId;
    }

    @JsonProperty("L_name")
    public String getLName() {
        return lName;
    }

    @JsonProperty("L_name")
    public void setLName(String lName) {
        this.lName = lName;
    }

    @JsonProperty("L_city")
    public String getCity() {
        return lCity;
    }

    @JsonProperty("L_city")
    public void setLCity(String lCity) {
        this.lCity = lCity;
    }
}
