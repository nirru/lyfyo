package oxilo.com.lyfyo;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by nikk on 29/6/17.
 */

public class LyfoPrefs {
    // MY_PREFS_NAME - a static String variable like:
     public static final String MY_PREFS_NAME = "lyfo_prefs";
    public static final String CITY_KEY="city_key";
    public static final String CITY_LAT="city_lat";
    public static final String CITY_LNG="city_lng";
    public static final String CITY_ADMIN_AREA="city_admin_area";
    public static final String FILTER_OFFER="offer";
    public static final String FILTER_RATING="rating";
    public static final String FILTER_POPULAR="popular";
    public static final String FILTER_RECENTLY_ADD="recently_Add";
    public static final String FILTER_ORDER_BY_COST="order_by_cost";
    public static final String FILTER_GENDER="gender";
    public static final String FILTER_CSV="csv";

    public LyfoPrefs(){

    }

    public  SharedPreferences.Editor getEditor(Context context){
        return context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
    }

    public  SharedPreferences getLyfoPrefs(Context context){
        return context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
    }

    public  void saveCity(String city,Context context){
        getEditor(context).putString(CITY_KEY,city).apply();
    }

    public  String getCity(Context context){
       return getLyfoPrefs(context).getString(CITY_KEY,"jaipur");
    }

    public  void saveAdminArea(String admin,Context context){
        getEditor(context).putString(CITY_ADMIN_AREA,admin).apply();
    }

    public  String getAdminArea(Context context){
        return getLyfoPrefs(context).getString(CITY_ADMIN_AREA,"jaipur");
    }

    public  void saveOfferVariable(String offer,Context context){
        getEditor(context).putString(FILTER_OFFER,offer).apply();
    }

    public  String getOfferVariable(Context context){
        return getLyfoPrefs(context).getString(FILTER_OFFER,"on");
    }

    public  void saveRatingVariable(String rating,Context context){
        getEditor(context).putString(FILTER_RATING,rating).apply();
    }

    public  String getRatingVariable(Context context){
        return getLyfoPrefs(context).getString(FILTER_RATING,"high");
    }

    public  void savePopularVariable(String popular,Context context){
        getEditor(context).putString(FILTER_POPULAR,popular).apply();
    }

    public  String getPopularVariable(Context context){
        return getLyfoPrefs(context).getString(FILTER_POPULAR,"high");
    }

    public  void saveRecentlyAddVariable(String recentlyadd,Context context){
        getEditor(context).putString(FILTER_RECENTLY_ADD,recentlyadd).apply();
    }

    public  String getRecentlyAddVariable(Context context){
        return getLyfoPrefs(context).getString(FILTER_RECENTLY_ADD,"on");
    }

    public  void saveSortByCostVariable(String sortByCost,Context context){
        getEditor(context).putString(FILTER_ORDER_BY_COST,sortByCost).apply();
    }

    public  String getSortByCostVariable(Context context){
        return getLyfoPrefs(context).getString(FILTER_ORDER_BY_COST,"high");
    }

    public  void saveGenderVariable(String gender,Context context){
        getEditor(context).putString(FILTER_GENDER,gender).apply();
    }

    public int getGenderVariable(Context context){
        return getLyfoPrefs(context).getInt(FILTER_GENDER,1);
    }

    public  void saveCsvVariable(String csv,Context context){
        getEditor(context).putString(FILTER_CSV,csv).apply();
    }

    public String getCsvVariable(Context context){
        return getLyfoPrefs(context).getString(FILTER_CSV,"Facial");
    }

    public  void saveLat(float lat,Context context){
        getEditor(context).putFloat(CITY_LAT,lat).apply();
    }

    public  void saveLng(float lng,Context context){
        getEditor(context).putFloat(CITY_LNG,lng).apply();
    }

    public float getLat(Context context){
        return getLyfoPrefs(context).getFloat(CITY_LAT,0.0f);
    }

    public float getlng(Context context){
        return getLyfoPrefs(context).getFloat(CITY_LNG,0.0f);
    }
}
