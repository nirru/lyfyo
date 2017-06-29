package oxilo.com.lyfyo.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import oxilo.com.lyfyo.City;
import oxilo.com.lyfyo.LyfoPrefs;
import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.network.api.ServiceFactory;
import oxilo.com.lyfyo.network.api.WebService;
import oxilo.com.lyfyo.ui.common.BaseActivity;
import oxilo.com.lyfyo.ui.fragments.DetailFragment;
import oxilo.com.lyfyo.ui.fragments.HomeFragment;
import oxilo.com.lyfyo.ui.modal.PopularLocation;
import oxilo.com.lyfyo.ui.modal.Salon;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    public ArrayList<PopularLocation> popularLocations;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getToolbar().setVisibility(View.GONE);
         popularLocations = new ArrayList<>();
        if (savedInstanceState == null)
            startFragment(HomeFragment.newInstance("",""));
        getPopularLocation();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void getPopularLocation(){
        LyfoPrefs lyfoPrefs = new LyfoPrefs();
        lyfoPrefs.getLyfoPrefs(MainActivity.this);
        String city = lyfoPrefs.getCity(MainActivity.this);

        if (city.equals(City.MUMBAI.toString()) || city.equals(City.PUNE.toString())
                || city.equals(City.NAVIMUMBAI.toString()) || city.equals(City.BHUNESWAR.toString())
            || city.equals(City.CHENNAI.toString()))
        {

            try {
                WebService webService = ServiceFactory.createRetrofitService(WebService.class);
                webService.popularLocation(lyfoPrefs.getLat(MainActivity.this),lyfoPrefs.getlng(MainActivity.this))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Response<ResponseBody>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull Response<ResponseBody> responseBodyResponse) {
                                String sd = null;
                                try {
                                    sd = new String(responseBodyResponse.body().bytes());
                                    JSONObject mapping = new JSONObject(sd);
                                    ObjectMapper mapper = new ObjectMapper();
                                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                                    if (mapping.getInt("error_Code")==0){
                                       popularLocations = mapper.readValue(mapping.getString("Service"), new TypeReference<List<PopularLocation>>(){});
                                       Log.e("SIZE==","" + popularLocations.size());
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        else{

        }
    }
}
