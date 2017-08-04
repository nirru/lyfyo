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
import oxilo.com.lyfyo.ui.fragments.HomeFragment;
import oxilo.com.lyfyo.ui.modal.PopularLocation;
import retrofit2.Response;

public class MainActivity extends BaseActivity {


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getToolbar().setVisibility(View.GONE);
        if (savedInstanceState == null)
            startFragment(HomeFragment.newInstance("",""));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LyfoPrefs lyfoPrefs = new LyfoPrefs();
        lyfoPrefs.getEditor(MainActivity.this);
        lyfoPrefs.saveLocationVariable(true,MainActivity.this);
    }
}

