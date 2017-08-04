package oxilo.com.lyfyo.ui.modal;

/**
 * Created by nikk on 26/7/17.
 */

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "OF_Id",
        "offertype",
        "OF_fromDate",
        "OF_toDate",
        "OF_fromTime",
        "OF_toTime",
        "OF_description",
        "OF_ValueTotal",
        "OF_OfferValue"
})
public class Offer implements Parcelable
{

    @JsonProperty("OF_Id")
    private String oFId;
    @JsonProperty("offertype")
    private String offertype;
    @JsonProperty("OF_fromDate")
    private String oFFromDate;
    @JsonProperty("OF_toDate")
    private String oFToDate;
    @JsonProperty("OF_fromTime")
    private String oFFromTime;
    @JsonProperty("OF_toTime")
    private String oFToTime;
    @JsonProperty("OF_description")
    private String oFDescription;
    @JsonProperty("OF_ValueTotal")
    private String oFValueTotal;
    @JsonProperty("OF_OfferValue")
    private String oFOfferValue;
    public final static Parcelable.Creator<Offer> CREATOR = new Creator<Offer>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Offer createFromParcel(Parcel in) {
            Offer instance = new Offer();
            instance.oFId = ((String) in.readValue((String.class.getClassLoader())));
            instance.offertype = ((String) in.readValue((String.class.getClassLoader())));
            instance.oFFromDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.oFToDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.oFFromTime = ((String) in.readValue((String.class.getClassLoader())));
            instance.oFToTime = ((String) in.readValue((String.class.getClassLoader())));
            instance.oFDescription = ((String) in.readValue((String.class.getClassLoader())));
            instance.oFValueTotal = ((String) in.readValue((String.class.getClassLoader())));
            instance.oFOfferValue = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Offer[] newArray(int size) {
            return (new Offer[size]);
        }

    }
            ;

    @JsonProperty("OF_Id")
    public String getOFId() {
        return oFId;
    }

    @JsonProperty("OF_Id")
    public void setOFId(String oFId) {
        this.oFId = oFId;
    }

    @JsonProperty("offertype")
    public String getOffertype() {
        return offertype;
    }

    @JsonProperty("offertype")
    public void setOffertype(String offertype) {
        this.offertype = offertype;
    }

    @JsonProperty("OF_fromDate")
    public String getOFFromDate() {
        return oFFromDate;
    }

    @JsonProperty("OF_fromDate")
    public void setOFFromDate(String oFFromDate) {
        this.oFFromDate = oFFromDate;
    }

    @JsonProperty("OF_toDate")
    public String getOFToDate() {
        return oFToDate;
    }

    @JsonProperty("OF_toDate")
    public void setOFToDate(String oFToDate) {
        this.oFToDate = oFToDate;
    }

    @JsonProperty("OF_fromTime")
    public String getOFFromTime() {
        return oFFromTime;
    }

    @JsonProperty("OF_fromTime")
    public void setOFFromTime(String oFFromTime) {
        this.oFFromTime = oFFromTime;
    }

    @JsonProperty("OF_toTime")
    public String getOFToTime() {
        return oFToTime;
    }

    @JsonProperty("OF_toTime")
    public void setOFToTime(String oFToTime) {
        this.oFToTime = oFToTime;
    }

    @JsonProperty("OF_description")
    public String getOFDescription() {
        return oFDescription;
    }

    @JsonProperty("OF_description")
    public void setOFDescription(String oFDescription) {
        this.oFDescription = oFDescription;
    }

    @JsonProperty("OF_ValueTotal")
    public String getOFValueTotal() {
        return oFValueTotal;
    }

    @JsonProperty("OF_ValueTotal")
    public void setOFValueTotal(String oFValueTotal) {
        this.oFValueTotal = oFValueTotal;
    }

    @JsonProperty("OF_OfferValue")
    public String getOFOfferValue() {
        return oFOfferValue;
    }

    @JsonProperty("OF_OfferValue")
    public void setOFOfferValue(String oFOfferValue) {
        this.oFOfferValue = oFOfferValue;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(oFId);
        dest.writeValue(offertype);
        dest.writeValue(oFFromDate);
        dest.writeValue(oFToDate);
        dest.writeValue(oFFromTime);
        dest.writeValue(oFToTime);
        dest.writeValue(oFDescription);
        dest.writeValue(oFValueTotal);
        dest.writeValue(oFOfferValue);
    }

    public int describeContents() {
        return 0;
    }

}
