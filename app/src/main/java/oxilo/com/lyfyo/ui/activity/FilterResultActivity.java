package oxilo.com.lyfyo.ui.activity;

import android.content.Intent;
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
import oxilo.com.lyfyo.ui.fragments.FilterResultFragment;
import oxilo.com.lyfyo.ui.fragments.HomeFragment;
import oxilo.com.lyfyo.ui.modal.PopularLocation;
import retrofit2.Response;

import static android.R.attr.data;

public class FilterResultActivity extends BaseActivity implements FilterResultFragment.OnFragmentInteractionListener {

    public ArrayList<PopularLocation> popularLocations;
    private String offer;
    private String rating;
    private String popular;
    private String recently_add;
    private String order_by_cost;
    private int gender;
    private String csv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getToolbar().setVisibility(View.GONE);
        if (getIntent()!=null){
            Bundle data = getIntent().getExtras();
            offer = data.getString("offer");
            rating = data.getString("rating");
            popular = data.getString("pouplar");
            recently_add = data.getString("recently");
            order_by_cost = data.getString("sortbycost");
            gender = data.getInt("gender", 1);
            csv = data.getString("csv");
        }
         popularLocations = new ArrayList<>();

       FilterResultFragment fi = FilterResultFragment.newInstance("","");
        fi.setValue(offer ,rating,popular,recently_add,order_by_cost,gender,csv);
            startFragment(fi);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

