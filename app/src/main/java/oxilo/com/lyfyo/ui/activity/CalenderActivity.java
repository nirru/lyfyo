package oxilo.com.lyfyo.ui.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.ui.common.BaseActivity;
import oxilo.com.lyfyo.ui.fragments.CalenderFragment;
import oxilo.com.lyfyo.ui.fragments.HomeFragment;

public class CalenderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getToolbar().setVisibility(View.GONE);
        if (savedInstanceState == null)
            startFragment(CalenderFragment.newInstance("",""));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
