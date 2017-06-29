package oxilo.com.lyfyo.network.api;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by ericbasendra on 10/06/16.
 */
public interface WebService {
    @FormUrlEncoded
    @POST("index.php/APII/SalonList")
    io.reactivex.Observable<Response<ResponseBody>> filter(
            @Field("shortByCost") String shortByCost,
            @Field("rating") String rating,
            @Field("recentAdd") String recentAdd,
            @Field("service") String service,
            @Field("offer") String offer,
            @Field("gender") String gender,
            @Field("popular") String popular,
            @Field("page") String page);

    @FormUrlEncoded
    @POST("index.php/APII/Service")
    io.reactivex.Observable<Response<ResponseBody>> findService(
            @Field("serviceName") String serviceName,
            @Field("page") String page);

    @FormUrlEncoded
    @POST("index.php/APII/Location")
    io.reactivex.Observable<Response<ResponseBody>> findLocation(
            @Field("location") String location,
            @Field("page") int page);

    @FormUrlEncoded
    @POST("index.php/APII/SalonByLocation")
    io.reactivex.Observable<Response<ResponseBody>> sallonByLocation(
            @Field("location") String location,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("page") String page);

//    @FormUrlEncoded
    @GET("index.php/APII/SalonList")
    io.reactivex.Observable<Response<ResponseBody>> sallonList(
//            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("index.php/APII/SalonByList")
    io.reactivex.Observable<Response<ResponseBody>> sallonDetail(
            @Field("salonId") String salonId,
            @Field("page") int page);

    @FormUrlEncoded
    @POST("index.php/APII/PopularLocation")
    io.reactivex.Observable<Response<ResponseBody>> popularLocation(
            @Field("latitude") double latitude,
            @Field("longitude") double longitude);
}