package oxilo.com.lyfyo.ui.modal;

/**
 * Created by nikk on 3/8/17.
 */

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "pckcart_id",
        "OF_Highlights",
        "OF_description",
        "pckcart_totalprice",
        "pckcart_valueprice",
        "package_discount",
        "images",
        "service"
})
public class Package implements Parcelable
{

    @JsonProperty("pckcart_id")
    private String pckcartId;
    @JsonProperty("OF_Highlights")
    private String oFHighlights;
    @JsonProperty("OF_description")
    private String oFDescription;
    @JsonProperty("pckcart_totalprice")
    private String pckcartTotalprice;
    @JsonProperty("pckcart_valueprice")
    private String pckcartValueprice;
    @JsonProperty("package_discount")
    private String packageDiscount;
    @JsonProperty("images")
    private String images;
    @JsonProperty("service")
    private List<Service> service = new ArrayList<>();

    private boolean is_Detail_Open = false;
    private boolean is_add_button_clicked = false;
    public final static Parcelable.Creator<Package> CREATOR = new Creator<Package>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Package createFromParcel(Parcel in) {
            Package instance = new Package();
            instance.pckcartId = ((String) in.readValue((String.class.getClassLoader())));
            instance.oFHighlights = ((String) in.readValue((String.class.getClassLoader())));
            instance.oFDescription = ((String) in.readValue((String.class.getClassLoader())));
            instance.pckcartTotalprice = ((String) in.readValue((String.class.getClassLoader())));
            instance.pckcartValueprice = ((String) in.readValue((String.class.getClassLoader())));
            instance.packageDiscount = ((String) in.readValue((String.class.getClassLoader())));
            instance.images = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.service, (Service.class.getClassLoader()));
            return instance;
        }

        public Package[] newArray(int size) {
            return (new Package[size]);
        }

    };

    @JsonProperty("pckcart_id")
    public String getPckcartId() {
        return pckcartId;
    }

    @JsonProperty("pckcart_id")
    public void setPckcartId(String pckcartId) {
        this.pckcartId = pckcartId;
    }

    @JsonProperty("OF_Highlights")
    public String getOFHighlights() {
        return oFHighlights;
    }

    @JsonProperty("OF_Highlights")
    public void setOFHighlights(String oFHighlights) {
        this.oFHighlights = oFHighlights;
    }

    @JsonProperty("OF_description")
    public String getOFDescription() {
        return oFDescription;
    }

    @JsonProperty("OF_description")
    public void setOFDescription(String oFDescription) {
        this.oFDescription = oFDescription;
    }

    @JsonProperty("pckcart_totalprice")
    public String getPckcartTotalprice() {
        return pckcartTotalprice;
    }

    @JsonProperty("pckcart_totalprice")
    public void setPckcartTotalprice(String pckcartTotalprice) {
        this.pckcartTotalprice = pckcartTotalprice;
    }

    @JsonProperty("pckcart_valueprice")
    public String getPckcartValueprice() {
        return pckcartValueprice;
    }

    @JsonProperty("pckcart_valueprice")
    public void setPckcartValueprice(String pckcartValueprice) {
        this.pckcartValueprice = pckcartValueprice;
    }

    @JsonProperty("package_discount")
    public String getPackageDiscount() {
        return packageDiscount;
    }

    @JsonProperty("package_discount")
    public void setPackageDiscount(String packageDiscount) {
        this.packageDiscount = packageDiscount;
    }

    @JsonProperty("images")
    public String getImages() {
        return images;
    }

    @JsonProperty("images")
    public void setImages(String images) {
        this.images = images;
    }

    @JsonProperty("service")
    public List<Service> getService() {
        return service;
    }

    @JsonProperty("service")
    public void setService(List<Service> service) {
        this.service = service;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pckcartId);
        dest.writeValue(oFHighlights);
        dest.writeValue(pckcartTotalprice);
        dest.writeValue(pckcartValueprice);
        dest.writeValue(packageDiscount);
        dest.writeValue(images);
        dest.writeList(service);
    }

    public int describeContents() {
        return 0;
    }


    public boolean is_Detail_Open() {
        return is_Detail_Open;
    }

    public void setIs_Detail_Open(boolean is_Detail_Open) {
        this.is_Detail_Open = is_Detail_Open;
    }

    public boolean is_add_button_clicked() {
        return is_add_button_clicked;
    }

    public void setIs_add_button_clicked(boolean is_add_button_clicked) {
        this.is_add_button_clicked = is_add_button_clicked;
    }
}
