package oxilo.com.lyfyo.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.ui.dialog.PhoneNumber;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.mobile_number)
    public void onViewClicked() {
        PhoneNumber phoneNumber = PhoneNumber.newInstance("", "");
        phoneNumber.show(getSupportFragmentManager(), phoneNumber.getTag());
    }


}
