package oxilo.com.lyfyo.ui.modal;

/**
 * Created by nikk on 5/8/17.
 */
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Id",
        "businessName",
        "location",
        "sl_type",
        "distance",
        "sl_rating",
        "sl_vote",
        "is_Offer",
        "Bimg_prim_img"
})

public class Result {

    @JsonProperty("Id")
    private String id;
    @JsonProperty("businessName")
    private String businessName;
    @JsonProperty("location")
    private String location;
    @JsonProperty("sl_type")
    private String slType;
    @JsonProperty("distance")
    private String distance;
    @JsonProperty("sl_rating")
    private Integer slRating;
    @JsonProperty("sl_vote")
    private String slVote;
    @JsonProperty("is_Offer")
    private Integer isOffer;
    @JsonProperty("Bimg_prim_img")
    private String bimgPrimImg;

    @JsonProperty("Id")
    public String getId() {
        return id;
    }

    @JsonProperty("Id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("businessName")
    public String getBusinessName() {
        return businessName;
    }

    @JsonProperty("businessName")
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("sl_type")
    public String getSlType() {
        return slType;
    }

    @JsonProperty("sl_type")
    public void setSlType(String slType) {
        this.slType = slType;
    }

    @JsonProperty("distance")
    public String getDistance() {
        return distance;
    }

    @JsonProperty("distance")
    public void setDistance(String distance) {
        this.distance = distance;
    }

    @JsonProperty("sl_rating")
    public Integer getSlRating() {
        return slRating;
    }

    @JsonProperty("sl_rating")
    public void setSlRating(Integer slRating) {
        this.slRating = slRating;
    }

    @JsonProperty("sl_vote")
    public String getSlVote() {
        return slVote;
    }

    @JsonProperty("sl_vote")
    public void setSlVote(String slVote) {
        this.slVote = slVote;
    }

    @JsonProperty("is_Offer")
    public Integer getIsOffer() {
        return isOffer;
    }

    @JsonProperty("is_Offer")
    public void setIsOffer(Integer isOffer) {
        this.isOffer = isOffer;
    }

    @JsonProperty("Bimg_prim_img")
    public String getBimgPrimImg() {
        return bimgPrimImg;
    }

    @JsonProperty("Bimg_prim_img")
    public void setBimgPrimImg(String bimgPrimImg) {
        this.bimgPrimImg = bimgPrimImg;
    }

}
