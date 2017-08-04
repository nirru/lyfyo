package oxilo.com.lyfyo.ui.modal;

/**
 * Created by nikk on 12/7/17.
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "U_id",
        "U_mobile",
        "U_fname",
        "U_lname",
        "U_email"
})
public class UserDetail {

    @JsonProperty("U_id")
    private String uId;
    @JsonProperty("U_mobile")
    private String uMobile;
    @JsonProperty("U_fname")
    private String uFname;
    @JsonProperty("U_lname")
    private String uLname;
    @JsonProperty("U_email")
    private String uEmail;

    private String date;
    private String time;

    @JsonProperty("U_id")
    public String getUId() {
        return uId;
    }

    @JsonProperty("U_id")
    public void setUId(String uId) {
        this.uId = uId;
    }

    @JsonProperty("U_mobile")
    public String getUMobile() {
        return uMobile;
    }

    @JsonProperty("U_mobile")
    public void setUMobile(String uMobile) {
        this.uMobile = uMobile;
    }

    @JsonProperty("U_fname")
    public String getUFname() {
        return uFname;
    }

    @JsonProperty("U_fname")
    public void setUFname(String uFname) {
        this.uFname = uFname;
    }

    @JsonProperty("U_lname")
    public String getULname() {
        return uLname;
    }

    @JsonProperty("U_lname")
    public void setULname(String uLname) {
        this.uLname = uLname;
    }

    @JsonProperty("U_email")
    public String getUEmail() {
        return uEmail;
    }

    @JsonProperty("U_email")
    public void setUEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
