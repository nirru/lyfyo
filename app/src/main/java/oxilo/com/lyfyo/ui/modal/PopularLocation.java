package oxilo.com.lyfyo.ui.modal;

/**
 * Created by nikk on 29/6/17.
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "L_name",
        "salonCount",
        "distance"
})
public class PopularLocation {

    @JsonProperty("L_name")
    private String lName;
    @JsonProperty("salonCount")
    private String salonCount;
    @JsonProperty("distance")
    private String distance;

    @JsonProperty("L_name")
    public String getLName() {
        return lName;
    }

    @JsonProperty("L_name")
    public void setLName(String lName) {
        this.lName = lName;
    }

    @JsonProperty("salonCount")
    public String getSalonCount() {
        return salonCount;
    }

    @JsonProperty("salonCount")
    public void setSalonCount(String salonCount) {
        this.salonCount = salonCount;
    }

    @JsonProperty("distance")
    public String getDistance() {
        return distance;
    }

    @JsonProperty("distance")
    public void setDistance(String distance) {
        this.distance = distance;
    }

}
