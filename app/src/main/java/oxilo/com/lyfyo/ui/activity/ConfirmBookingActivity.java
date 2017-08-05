package oxilo.com.lyfyo.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import oxilo.com.lyfyo.ApplicationController;
import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.network.api.ServiceFactory;
import oxilo.com.lyfyo.network.api.WebService;
import oxilo.com.lyfyo.ui.modal.UserDetail;
import retrofit2.Response;

import static oxilo.com.lyfyo.AppConstant.USER_DETAIL;

public class ConfirmBookingActivity extends AppCompatActivity {
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_promo)
    EditText tvPromo;

    UserDetail userDetail;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.scroll)
    ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);
        ButterKnife.bind(this);

        setDetail();
    }

    private void setDetail() {
        userDetail = ApplicationController.getInstance().getAppPrefs().getObject(USER_DETAIL, UserDetail.class);

        if (!userDetail.getUMobile().equals("") && userDetail.getUMobile() != null) {
            tvContact.setText(userDetail.getUMobile());
        }
        if (!userDetail.getUFname().equals("") && userDetail.getUFname() != null) {
            tvName.setText(userDetail.getUFname());
        }

        if (!userDetail.getUEmail().equals("") && userDetail.getUEmail() != null) {
            tvEmail.setText(userDetail.getUEmail());
        } else {
            tvEmail.setText("no email found");
        }
        if (!userDetail.getDate().equals("") && userDetail.getDate() != null) {
            tvDate.setText(userDetail.getDate());
        }
        if (!userDetail.getTime().equals("") && userDetail.getTime() != null) {
            tvTime.setText(userDetail.getTime());
        }
        if ( userDetail.getPrice() != null) {
            tvPrice.setText(userDetail.getPrice());
        }
    }


    @OnClick({R.id.back, R.id.confirm_booking})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm_booking:
                bookAppoitment();
                break;
        }
    }

    public String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length() - 1);
    }

    private void bookAppoitment() {
        try {
            showProgress(true);
            WebService webService = ServiceFactory.createRetrofitService(WebService.class);
            webService.bookAppointment(userDetail.getBu_id(), userDetail.getUId(), userDetail.getDate(), userDetail.getTime(), userDetail.getTime(), "", "", userDetail.getPackage_id())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Response<ResponseBody>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull Response<ResponseBody> responseBodyResponse) {
                            showProgress(false);
                            try {
                                String sd = new String(responseBodyResponse.body().bytes());
                                JSONObject mapping = new JSONObject(sd);
                                if (mapping.getString("status").equals("success")) {
                                    Toast.makeText(ConfirmBookingActivity.this, "your appointment has schduled", Toast.LENGTH_SHORT).show();
                                }
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(ConfirmBookingActivity.this, MainActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(i);
                                        finish();
                                    }
                                }, 2000);
                            } catch (Exception ex) {
                                ex.printStackTrace();
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

                scroll.setVisibility(show ? View.GONE : View.VISIBLE);
                scroll.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        scroll.setVisibility(show ? View.GONE : View.VISIBLE);
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
                scroll.setVisibility(show ? View.VISIBLE : View.GONE);
                scroll.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
