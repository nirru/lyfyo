package oxilo.com.lyfyo.ui.modal;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by nikk on 20/7/17.
 */

public class ZipModal {

    private Response<ResponseBody> sallonResponse;
    private Response<ResponseBody> pcResponse;

    public Response<ResponseBody> getSallonResponse() {
        return sallonResponse;
    }

    public void setSallonResponse(Response<ResponseBody> sallonResponse) {
        this.sallonResponse = sallonResponse;
    }

    public Response<ResponseBody> getPcResponse() {
        return pcResponse;
    }

    public void setPcResponse(Response<ResponseBody> pcResponse) {
        this.pcResponse = pcResponse;
    }
}
