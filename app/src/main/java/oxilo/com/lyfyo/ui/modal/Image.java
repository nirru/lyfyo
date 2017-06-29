package oxilo.com.lyfyo.ui.modal;

/**
 * Created by nikk on 27/6/17.
 */

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Bimg_id",
        "Bimg_prim_img",
        "Bimg_sec_img"
})
public class Image implements Parcelable
{

    @JsonProperty("Bimg_id")
    private String bimgId;
    @JsonProperty("Bimg_prim_img")
    private String bimgPrimImg;
    @JsonProperty("Bimg_sec_img")
    private String bimgSecImg;
    public final static Parcelable.Creator<Image> CREATOR = new Creator<Image>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Image createFromParcel(Parcel in) {
            Image instance = new Image();
            instance.bimgId = ((String) in.readValue((String.class.getClassLoader())));
            instance.bimgPrimImg = ((String) in.readValue((String.class.getClassLoader())));
            instance.bimgSecImg = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Image[] newArray(int size) {
            return (new Image[size]);
        }

    }
            ;

    @JsonProperty("Bimg_id")
    public String getBimgId() {
        return bimgId;
    }

    @JsonProperty("Bimg_id")
    public void setBimgId(String bimgId) {
        this.bimgId = bimgId;
    }

    @JsonProperty("Bimg_prim_img")
    public String getBimgPrimImg() {
        return bimgPrimImg;
    }

    @JsonProperty("Bimg_prim_img")
    public void setBimgPrimImg(String bimgPrimImg) {
        this.bimgPrimImg = bimgPrimImg;
    }

    @JsonProperty("Bimg_sec_img")
    public String getBimgSecImg() {
        return bimgSecImg;
    }

    @JsonProperty("Bimg_sec_img")
    public void setBimgSecImg(String bimgSecImg) {
        this.bimgSecImg = bimgSecImg;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(bimgId);
        dest.writeValue(bimgPrimImg);
        dest.writeValue(bimgSecImg);
    }

    public int describeContents() {
        return 0;
    }

}