package oxilo.com.lyfyo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import oxilo.com.lyfyo.ApplicationController;
import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.ui.modal.UserDetail;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);
        ButterKnife.bind(this);

        setDetail();
    }

    private void setDetail(){
        UserDetail userDetail = ApplicationController.getInstance().getAppPrefs().getObject(USER_DETAIL,UserDetail.class);
        if (!userDetail.getUMobile().equals("") && userDetail.getUMobile()!=null){
            tvContact.setText(userDetail.getUMobile());
        }
        if (!userDetail.getUFname().equals("") && userDetail.getUFname()!=null){
            tvName.setText(userDetail.getUFname());
        }

        if (!userDetail.getUEmail().equals("") && userDetail.getUEmail()!=null){
            tvEmail.setText(userDetail.getUEmail());
        }
        else {
            tvEmail.setText("no email found");
        }
        if (!userDetail.getDate().equals("") && userDetail.getDate()!=null){
            tvDate.setText(userDetail.getDate());
        }
        if (!userDetail.getTime().equals("") && userDetail.getTime()!=null){
            tvTime.setText(userDetail.getTime());
        }
    }


    @OnClick({R.id.back, R.id.confirm_booking})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm_booking:
                break;
        }
    }
}
