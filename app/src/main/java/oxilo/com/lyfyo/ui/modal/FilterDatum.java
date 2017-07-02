package oxilo.com.lyfyo.ui.modal;

/**
 * Created by nikk on 1/7/17.
 */
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Id",
        "BU_servicefor",
        "code",
        "businessName",
        "ownerName",
        "managerName",
        "email",
        "phone",
        "mobile",
        "tag",
        "houseno",
        "address1",
        "address2",
        "location",
        "landmark",
        "pincode",
        "cost",
        "avgcost",
        "distance",
        "sl_type",
        "sl_rating",
        "category",
        "images"
})
public class FilterDatum implements Parcelable
{

    @JsonProperty("Id")
    private String id;
    @JsonProperty("BU_servicefor")
    private String bUServicefor;
    @JsonProperty("code")
    private String code;
    @JsonProperty("businessName")
    private String businessName;
    @JsonProperty("ownerName")
    private String ownerName;
    @JsonProperty("managerName")
    private String managerName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("tag")
    private String tag;
    @JsonProperty("houseno")
    private String houseno;
    @JsonProperty("address1")
    private String address1;
    @JsonProperty("address2")
    private String address2;
    @JsonProperty("location")
    private String location;
    @JsonProperty("landmark")
    private String landmark;
    @JsonProperty("pincode")
    private String pincode;
    @JsonProperty("cost")
    private String cost;
    @JsonProperty("avgcost")
    private String avgcost;
    @JsonProperty("distance")
    private String distance;
    @JsonProperty("sl_type")
    private String slType;
    @JsonProperty("sl_rating")
    private String slRating;
    @JsonProperty("category")
    private List<Category> category = null;
    @JsonProperty("images")
    private List<Image> images = null;
    public final static Parcelable.Creator<FilterDatum> CREATOR = new Creator<FilterDatum>() {


        @SuppressWarnings({
                "unchecked"
        })
        public FilterDatum createFromParcel(Parcel in) {
            FilterDatum instance = new FilterDatum();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.bUServicefor = ((String) in.readValue((String.class.getClassLoader())));
            instance.code = ((String) in.readValue((String.class.getClassLoader())));
            instance.businessName = ((String) in.readValue((String.class.getClassLoader())));
            instance.ownerName = ((String) in.readValue((String.class.getClassLoader())));
            instance.managerName = ((String) in.readValue((String.class.getClassLoader())));
            instance.email = ((String) in.readValue((String.class.getClassLoader())));
            instance.phone = ((String) in.readValue((String.class.getClassLoader())));
            instance.mobile = ((String) in.readValue((String.class.getClassLoader())));
            instance.tag = ((String) in.readValue((String.class.getClassLoader())));
            instance.houseno = ((String) in.readValue((String.class.getClassLoader())));
            instance.address1 = ((String) in.readValue((String.class.getClassLoader())));
            instance.address2 = ((String) in.readValue((String.class.getClassLoader())));
            instance.location = ((String) in.readValue((String.class.getClassLoader())));
            instance.landmark = ((String) in.readValue((String.class.getClassLoader())));
            instance.pincode = ((String) in.readValue((String.class.getClassLoader())));
            instance.cost = ((String) in.readValue((String.class.getClassLoader())));
            instance.avgcost = ((String) in.readValue((String.class.getClassLoader())));
            instance.distance = ((String) in.readValue((String.class.getClassLoader())));
            instance.slType = ((String) in.readValue((String.class.getClassLoader())));
            instance.slRating = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.category, (Category.class.getClassLoader()));
            in.readList(instance.images, (Image.class.getClassLoader()));
            return instance;
        }

        public FilterDatum[] newArray(int size) {
            return (new FilterDatum[size]);
        }

    }
            ;

    @JsonProperty("Id")
    public String getId() {
        return id;
    }

    @JsonProperty("Id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("BU_servicefor")
    public String getBUServicefor() {
        return bUServicefor;
    }

    @JsonProperty("BU_servicefor")
    public void setBUServicefor(String bUServicefor) {
        this.bUServicefor = bUServicefor;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("businessName")
    public String getBusinessName() {
        return businessName;
    }

    @JsonProperty("businessName")
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    @JsonProperty("ownerName")
    public String getOwnerName() {
        return ownerName;
    }

    @JsonProperty("ownerName")
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @JsonProperty("managerName")
    public String getManagerName() {
        return managerName;
    }

    @JsonProperty("managerName")
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("mobile")
    public String getMobile() {
        return mobile;
    }

    @JsonProperty("mobile")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @JsonProperty("tag")
    public String getTag() {
        return tag;
    }

    @JsonProperty("tag")
    public void setTag(String tag) {
        this.tag = tag;
    }

    @JsonProperty("houseno")
    public String getHouseno() {
        return houseno;
    }

    @JsonProperty("houseno")
    public void setHouseno(String houseno) {
        this.houseno = houseno;
    }

    @JsonProperty("address1")
    public String getAddress1() {
        return address1;
    }

    @JsonProperty("address1")
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @JsonProperty("address2")
    public String getAddress2() {
        return address2;
    }

    @JsonProperty("address2")
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("landmark")
    public String getLandmark() {
        return landmark;
    }

    @JsonProperty("landmark")
    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    @JsonProperty("pincode")
    public String getPincode() {
        return pincode;
    }

    @JsonProperty("pincode")
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @JsonProperty("cost")
    public String getCost() {
        return cost;
    }

    @JsonProperty("cost")
    public void setCost(String cost) {
        this.cost = cost;
    }

    @JsonProperty("avgcost")
    public String getAvgcost() {
        return avgcost;
    }

    @JsonProperty("avgcost")
    public void setAvgcost(String avgcost) {
        this.avgcost = avgcost;
    }

    @JsonProperty("distance")
    public String getDistance() {
        return distance;
    }

    @JsonProperty("distance")
    public void setDistance(String distance) {
        this.distance = distance;
    }

    @JsonProperty("sl_type")
    public String getSlType() {
        return slType;
    }

    @JsonProperty("sl_type")
    public void setSlType(String slType) {
        this.slType = slType;
    }

    @JsonProperty("sl_rating")
    public String getSlRating() {
        return slRating;
    }

    @JsonProperty("sl_rating")
    public void setSlRating(String slRating) {
        this.slRating = slRating;
    }

    @JsonProperty("category")
    public List<Category> getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(List<Category> category) {
        this.category = category;
    }

    @JsonProperty("images")
    public List<Image> getImages() {
        return images;
    }

    @JsonProperty("images")
    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(bUServicefor);
        dest.writeValue(code);
        dest.writeValue(businessName);
        dest.writeValue(ownerName);
        dest.writeValue(managerName);
        dest.writeValue(email);
        dest.writeValue(phone);
        dest.writeValue(mobile);
        dest.writeValue(tag);
        dest.writeValue(houseno);
        dest.writeValue(address1);
        dest.writeValue(address2);
        dest.writeValue(location);
        dest.writeValue(landmark);
        dest.writeValue(pincode);
        dest.writeValue(cost);
        dest.writeValue(avgcost);
        dest.writeValue(distance);
        dest.writeValue(slType);
        dest.writeValue(slRating);
        dest.writeList(category);
        dest.writeList(images);
    }

    public int describeContents() {
        return 0;
    }

}