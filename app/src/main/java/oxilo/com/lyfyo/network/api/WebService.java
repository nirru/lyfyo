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
    @POST("index.php/APII/FilterData")
    io.reactivex.Observable<Response<ResponseBody>> filter(
            @Field("shortByCost") String shortByCost,
            @Field("rating") String rating,
            @Field("recentAdd") String recentAdd,
            @Field("service") String service,
            @Field("offer") String offer,
            @Field("gender") int gender,
            @Field("popular") String popular,
            @Field("location") String location,
            @Field("latitude") double latitude,
            @Field("longitude") double longitude,
            @Field("page") int page);

    @FormUrlEncoded
    @POST("index.php/APII/Service")
    io.reactivex.Observable<Response<ResponseBody>> findService(
            @Field("serviceName") String serviceName,
            @Field("page") int page);

    @FormUrlEncoded
    @POST("index.php/APII/Location")
    io.reactivex.Observable<Response<ResponseBody>> findLocation(
            @Field("location") String location,
            @Field("page") int page);

    @FormUrlEncoded
    @POST("index.php/APII/SalonByLocation")
    io.reactivex.Observable<Response<ResponseBody>> sallonByLocation(
            @Field("location") String location,
            @Field("latitude") double latitude,
            @Field("longitude") double longitude,
            @Field("page") int page);

    @FormUrlEncoded
    @POST("index.php/APII/PresentingCollection")
    io.reactivex.Observable<Response<ResponseBody>> presentingCollection(
            @Field("sl_city") String location);

    @FormUrlEncoded
    @POST("index.php/APII/PresentingCollectionByid")
    io.reactivex.Observable<Response<ResponseBody>> presentingCollectionById(
            @Field("pcId") String id,
            @Field("latitude") float latitude,
            @Field("longitude") float longitude);

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
            @Field("sl_city") String city);

    @FormUrlEncoded
    @POST("index.php/APII/ServiceAndSalonFilter")
    io.reactivex.Observable<Response<ResponseBody>> SearchSallon(
            @Field("filtername") String filtername,
            @Field("sl_location") String sl_location,
            @Field("latitude") float lat,
            @Field("longitude") float lng,
            @Field("page") int page);

    @FormUrlEncoded
    @POST("index.php/APII/Userlogin")
    io.reactivex.Observable<Response<ResponseBody>> getOtp(
            @Field("mobileNumber") String mobileNumber);

    @FormUrlEncoded
    @POST("index.php/APII/VerifyOTP")
    io.reactivex.Observable<Response<ResponseBody>> verifyOtp(
            @Field("mobileNumber") String mobileNumber,
            @Field("OTP") String otp,
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("email") String email);
}
