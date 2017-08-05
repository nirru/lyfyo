package oxilo.com.lyfyo.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import oxilo.com.lyfyo.ApplicationController;
import oxilo.com.lyfyo.LyfoPrefs;
import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.network.api.ServiceFactory;
import oxilo.com.lyfyo.network.api.WebService;
import oxilo.com.lyfyo.ui.fragments.DetailFragment;
import oxilo.com.lyfyo.ui.fragments.ServiceFragments;
import oxilo.com.lyfyo.ui.modal.FilterDatum;
import oxilo.com.lyfyo.ui.modal.Result;
import oxilo.com.lyfyo.ui.modal.UserDetail;
import retrofit2.Response;

import static oxilo.com.lyfyo.AppConstant.USER_DETAIL;

public class DetailActivity extends AppCompatActivity implements DetailFragment.OnFragmentInteractionListener, ServiceFragments.OnFragmentInteractionListener {

    FilterDatum filterDatum;
    @BindView(R.id.continue_rl)
    RelativeLayout continueRl;
    @BindView(R.id.price)
    TextView price;

    double total_price = 0.0;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.footer)
    LinearLayout footer;

    String id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        if (getIntent() != null){
            id = getIntent().getStringExtra("ID");
            getSallonDetail(id);
        }

//        if (savedInstanceState == null) {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.content, DetailFragment.newInstance(filterDatum, ""));
////            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @OnClick(R.id.continue_rl)
    public void onViewClicked() {
        LyfoPrefs lyfoPrefs = new LyfoPrefs();
        lyfoPrefs.getLyfoPrefs(DetailActivity.this);
        UserDetail userDetail = ApplicationController.getInstance().getAppPrefs().getObject(USER_DETAIL, UserDetail.class);
        if (userDetail == null) {
            userDetail = new UserDetail();
            userDetail.setLoggedIn(false);
            userDetail.setBu_id(id);
            userDetail.setPrice("" + getTotal());
        }else{
            userDetail.setBu_id(id);
            userDetail.setPrice("" + getTotal());
        }
        ApplicationController.getInstance().getAppPrefs().putObject(USER_DETAIL,userDetail);
        ApplicationController.getInstance().getAppPrefs().commit();
        if (price.getText().toString().equals("0.0")) {
            Toast.makeText(DetailActivity.this, "please add atleast one service", Toast.LENGTH_SHORT).show();
        } else {
            if (!userDetail.isLoggedIn()) {
                showLoginAlert(DetailActivity.this);
            } else {
                //go directly to date time page
                Intent i = new Intent(DetailActivity.this, CalenderActivity.class);
                startActivity(i);
            }
        }

    }

    public void showLoginAlert(final Context mContext) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);

        // Setting Dialog Title
        builder.setTitle("Login");
        builder.setMessage("Its seems that you are not login .Please login first");

        // On pressing Settings button
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(DetailActivity.this, LoginActivity.class);
                mContext.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        // display dialog
        dialog.show();
    }



    private void getSallonDetail(String id) {
        try {
            showProgress(true);
            WebService webService = ServiceFactory.createRetrofitService(WebService.class);
            webService.getDetailById(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Function<Response<ResponseBody>, FilterDatum>() {
                        @Override
                        public FilterDatum apply(@NonNull Response<ResponseBody> responseBodyResponse) throws Exception {
                            try {
                                String sd = new String(responseBodyResponse.body().bytes());
                                JSONObject mapping = new JSONObject(sd);
                                ObjectMapper mapper = new ObjectMapper();
                                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//                              mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
                                filterDatum = mapper.readValue(mapping.getString("Salon"), new TypeReference<FilterDatum>() {
                                });
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                            return filterDatum;
                        }
                    })
                    .subscribe(new Observer<FilterDatum>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull FilterDatum filterDatum) {
                            showProgress(false);
                            footer.setVisibility(View.VISIBLE);
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.content, DetailFragment.newInstance(filterDatum, ""));
//                          fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                int shortAnimTime = 200;

                content.setVisibility(show ? View.GONE : View.VISIBLE);
                content.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        content.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                });

                progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                progressBar.animate().setDuration(shortAnimTime).alpha(
                        show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
            } else {
                // The ViewPropertyAnimator APIs are not available, so simply show
                // and hide the relevant UI components.
                content.setVisibility(show ? View.VISIBLE : View.GONE);
                content.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public TextView getPriceView() {
        return price;
    }

    public double getTotal() {
        return total_price;
    }

    public void setTotal(double total) {
        total_price = total;
    }
}
