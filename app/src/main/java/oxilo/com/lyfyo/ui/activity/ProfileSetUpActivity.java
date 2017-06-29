package oxilo.com.lyfyo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import oxilo.com.lyfyo.R;

public class ProfileSetUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_set_up);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        Intent i= new Intent(ProfileSetUpActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
