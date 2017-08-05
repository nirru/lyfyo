package oxilo.com.lyfyo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.ui.common.BaseActivity;
import oxilo.com.lyfyo.ui.fragments.FilterFragment;
import oxilo.com.lyfyo.ui.fragments.ServiceFinderFragment;

public class FilterActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getToolbar().setVisibility(View.GONE);
        if (savedInstanceState == null)
            startFragment(FilterFragment.newInstance("",""));
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
