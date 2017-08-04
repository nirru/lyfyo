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
        "fk_serv_id",
        "pckcart_id",
        "pckcart_totalprice",
        "pckcart_valueprice",
        "pckcart_time",
        "images",
        "service"
})
public class Package implements Parcelable
{

    @JsonProperty("fk_serv_id")
    private String fkServId;
    @JsonProperty("pckcart_id")
    private String pckcartId;
    @JsonProperty("pckcart_totalprice")
    private String pckcartTotalprice;
    @JsonProperty("pckcart_valueprice")
    private String pckcartValueprice;
    @JsonProperty("pckcart_time")
    private String pckcartTime;
    @JsonProperty("images")
    private String images;
    @JsonProperty("service")
    private List<Service> service = new ArrayList<>();

    private boolean is_Detail_Open= false;
    public final static Parcelable.Creator<Package> CREATOR = new Creator<Package>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Package createFromParcel(Parcel in) {
            Package instance = new Package();
            instance.fkServId = ((String) in.readValue((String.class.getClassLoader())));
            instance.pckcartId = ((String) in.readValue((String.class.getClassLoader())));
            instance.pckcartTotalprice = ((String) in.readValue((String.class.getClassLoader())));
            instance.pckcartValueprice = ((String) in.readValue((String.class.getClassLoader())));
            instance.pckcartTime = ((String) in.readValue((String.class.getClassLoader())));
            instance.is_Detail_Open = ((Boolean) in.readValue((String.class.getClassLoader())));
            instance.images = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.service, (Service.class.getClassLoader()));
            return instance;
        }

        public Package[] newArray(int size) {
            return (new Package[size]);
        }

    }
            ;

    @JsonProperty("fk_serv_id")
    public String getFkServId() {
        return fkServId;
    }

    @JsonProperty("fk_serv_id")
    public void setFkServId(String fkServId) {
        this.fkServId = fkServId;
    }

    @JsonProperty("pckcart_id")
    public String getPckcartId() {
        return pckcartId;
    }

    @JsonProperty("pckcart_id")
    public void setPckcartId(String pckcartId) {
        this.pckcartId = pckcartId;
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

    @JsonProperty("pckcart_time")
    public String getPckcartTime() {
        return pckcartTime;
    }

    @JsonProperty("pckcart_time")
    public void setPckcartTime(String pckcartTime) {
        this.pckcartTime = pckcartTime;
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
        dest.writeValue(fkServId);
        dest.writeValue(pckcartId);
        dest.writeValue(pckcartTotalprice);
        dest.writeValue(pckcartValueprice);
        dest.writeValue(pckcartTime);
        dest.writeValue(is_Detail_Open);
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
}
