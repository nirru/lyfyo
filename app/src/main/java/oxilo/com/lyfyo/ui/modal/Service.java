package oxilo.com.lyfyo.ui.modal;

/**
 * Created by nikk on 30/6/17.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "SE_id",
        "SE_name",
        "SE_cost",
        "SE_description1",
        "SE_offer",
        "SE_offerPrice"
})
public class Service implements Parcelable{
    private boolean isSelected;

    private int count=0;

    @JsonProperty("SE_id")
    private String sEId;
    @JsonProperty("SE_name")
    private String sEName;
    @JsonProperty("SE_cost")
    private String sECost;
    @JsonProperty("SE_description1")
    private String sEDescription1;
    @JsonProperty("SE_offer")
    private String sEOffer;
    @JsonProperty("SE_offerPrice")
    private String sEOfferPrice;
    public final static Parcelable.Creator<Service> CREATOR = new Creator<Service>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Service createFromParcel(Parcel in) {
            Service instance = new Service();
            instance.sEId = ((String) in.readValue((String.class.getClassLoader())));
            instance.sEName = ((String) in.readValue((String.class.getClassLoader())));
            instance.sECost = ((String) in.readValue((String.class.getClassLoader())));
            instance.sEDescription1 = ((String) in.readValue((String.class.getClassLoader())));
            instance.sEOffer = ((String) in.readValue((String.class.getClassLoader())));
            instance.sEOfferPrice = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Service[] newArray(int size) {
            return (new Service[size]);
        }

    };


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

    @JsonProperty("SE_cost")
    public String getSECost() {
        return sECost;
    }

    @JsonProperty("SE_cost")
    public void setSECost(String sECost) {
        this.sECost = sECost;
    }

    @JsonProperty("SE_description1")
    public String getSEDescription1() {
        return sEDescription1;
    }

    @JsonProperty("SE_description1")
    public void setSEDescription1(String sEDescription1) {
        this.sEDescription1 = sEDescription1;
    }

    @JsonProperty("SE_offer")
    public String getSEOffer() {
        return sEOffer;
    }

    @JsonProperty("SE_offer")
    public void setSEOffer(String sEOffer) {
        this.sEOffer = sEOffer;
    }

    @JsonProperty("SE_offerPrice")
    public String getSEOfferPrice() {
        return sEOfferPrice;
    }

    @JsonProperty("SE_offerPrice")
    public void setSEOfferPrice(String sEOfferPrice) {
        this.sEOfferPrice = sEOfferPrice;
    }
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(sEId);
        dest.writeValue(sEName);
        dest.writeValue(sECost);
        dest.writeValue(sEDescription1);
        dest.writeValue(sEOffer);
        dest.writeValue(sEOfferPrice);
    }

    public int describeContents() {
        return 0;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
