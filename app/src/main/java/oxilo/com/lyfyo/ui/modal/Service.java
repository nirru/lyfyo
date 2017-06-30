package oxilo.com.lyfyo.ui.modal;

/**
 * Created by nikk on 30/6/17.
 */
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "SE_id",
        "SE_name"
})
public class Service {
    private boolean isSelected;

    @JsonProperty("SE_id")
    private String sEId;
    @JsonProperty("SE_name")
    private String sEName;

    @JsonProperty("SE_id")
    public String getSEId() {
        return sEId;
    }

    @JsonProperty("SE_id")
    public void setSEId(String sEId) {
        this.sEId = sEId;
    }

    @JsonProperty("SE_name")
    public String getSEName() {
        return sEName;
    }

    @JsonProperty("SE_name")
    public void setSEName(String sEName) {
        this.sEName = sEName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


}
