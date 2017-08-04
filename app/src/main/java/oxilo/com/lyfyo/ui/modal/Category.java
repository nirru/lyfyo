package oxilo.com.lyfyo.ui.modal;

/**
 * Created by nikk on 1/7/17.
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
        "CA_name",
        "CA_id",
        "service"
})
public class Category implements Parcelable
{

    @JsonProperty("CA_name")
    private String cAName;
    @JsonProperty("CA_id")
    private String cAId;
    @JsonProperty("service")
    private List<Service> service = new ArrayList<>();
    private List<Package> packages = new ArrayList<>();
    public final static Parcelable.Creator<Category> CREATOR = new Creator<Category>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Category createFromParcel(Parcel in) {
             Category instance = new Category();
            instance.cAName = ((String) in.readValue((String.class.getClassLoader())));
            instance.cAId = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.service, (Service.class.getClassLoader()));
            in.readList(instance.packages, (Package.class.getClassLoader()));
            return instance;
        }

        public Category[] newArray(int size) {
            return (new Category[size]);
        }

    }
            ;

    @JsonProperty("CA_name")
    public String getCAName() {
        return cAName;
    }

    @JsonProperty("CA_name")
    public void setCAName(String cAName) {
        this.cAName = cAName;
    }

    @JsonProperty("CA_id")
    public String getCAId() {
        return cAId;
    }

    @JsonProperty("CA_id")
    public void setCAId(String cAId) {
        this.cAId = cAId;
    }

    @JsonProperty("service")
    public List<Service> getService() {
        return service;
    }

    @JsonProperty("service")
    public void setService(List<Service> service) {
        this.service = service;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(cAName);
        dest.writeValue(cAId);
        dest.writeList(service);
        dest.writeList(packages);
    }

    public int describeContents() {
        return 0;
    }

}