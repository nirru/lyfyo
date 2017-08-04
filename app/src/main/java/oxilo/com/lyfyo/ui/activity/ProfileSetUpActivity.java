package oxilo.com.lyfyo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import oxilo.com.lyfyo.ApplicationController;
import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.network.api.ServiceFactory;
import oxilo.com.lyfyo.network.api.WebService;
import oxilo.com.lyfyo.ui.modal.FilterDatum;
import oxilo.com.lyfyo.ui.modal.UserDetail;
import retrofit2.Response;

import static oxilo.com.lyfyo.AppConstant.USER_DETAIL;

public class ProfileSetUpActivity extends AppCompatActivity {

    @BindView(R.id.usermobile)
    EditText usermobile;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.otp)
    EditText otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_set_up);
        ButterKnife.bind(this);
        if (getIntent() != null)
            usermobile.setText(getIntent().getStringExtra("mobile"));

        getOtp();
    }



    private void getOtp() {
        try {
            WebService webService = ServiceFactory.createRetrofitService(WebService.class);
            webService.getOtp(usermobile.getText().toString()).
                    subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Response<ResponseBody>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull Response<ResponseBody> responseBodyResponse) {

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

    private void submit(){
        try {
            name.setError(null);
            otp.setError(null);

            // Store values at the time of the login attempt.
            String error_invalid_name = name.getText().toString();
            String error_invalid_otp = otp.getText().toString();
            String error_invalid_email = email.getText().toString();
            String mobile_number = usermobile.getText().toString();

            boolean cancel = false;
            View focusView = null;

            if (TextUtils.isEmpty(error_invalid_name)) {
                name.setError("name can't be blank");
                focusView = name;
                cancel = true;
            }

            if (TextUtils.isEmpty(error_invalid_otp) && error_invalid_otp.length()==4) {
                otp.setError("otp must be 4 digit");
                focusView = otp;
                cancel = true;
            }

            if (TextUtils.isEmpty(error_invalid_email)) {
                error_invalid_email = "";
                cancel = false;
            }

            if (error_invalid_email.length()>0){
                if (!error_invalid_email.contains("@")){
                    email.setError("enter valid email");
                    focusView = email;
                    cancel = true;
                }
                else {
                    error_invalid_email = "";
                    cancel = false;
                }
            }

            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            } else {
                WebService webService = ServiceFactory.createRetrofitService(WebService.class);
                webService.verifyOtp(mobile_number,error_invalid_otp,error_invalid_name,"",error_invalid_email)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Response<ResponseBody>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull Response<ResponseBody> responseBodyResponse) {
                                try {
                                    String sd = new String(responseBodyResponse.body().bytes());
                                    JSONObject mapping = new JSONObject(sd);
                                    ObjectMapper mapper = new ObjectMapper();
                                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                                    UserDetail userDetail = mapper.readValue(mapping.getString("userDetail"), new TypeReference<UserDetail>() {});

                                    ApplicationController.getInstance().getAppPrefs().putObject(USER_DETAIL,userDetail);
                                    ApplicationController.getInstance().getAppPrefs().commit();
                                    Intent i = new Intent(ProfileSetUpActivity.this,CalenderActivity.class);
                                    startActivity(i);
                                    finish();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.resend_btn, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.resend_btn:
                getOtp();
                Toast.makeText(ProfileSetUpActivity.this,"wait for 5 -10 second.",Toast.LENGTH_SHORT).show();
                break;
            case R.id.submit:
                submit();
                break;
        }
    }
}
