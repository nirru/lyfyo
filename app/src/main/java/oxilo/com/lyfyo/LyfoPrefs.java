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
