package oxilo.com.lyfyo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import oxilo.com.lyfyo.R;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.textView2)
    public void onViewClicked() {
        finish();
    }

    @OnClick(R.id.appCompatButton)
    public void onButtonClicked() {
        finish();
    }
}
