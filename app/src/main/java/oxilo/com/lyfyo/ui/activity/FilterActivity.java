package oxilo.com.lyfyo.ui.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.ui.fragments.FilterFragment;
import oxilo.com.lyfyo.ui.fragments.ServiceFinderFragment;

public class FilterActivity extends AppCompatActivity implements FilterFragment.OnFragmentInteractionListener,
        ServiceFinderFragment.OnFragmentInteractionListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        if (savedInstanceState == null)
            startFragment(FilterFragment.newInstance("",""));
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void startFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
//        fragmentTransaction.addToBackStack(null);/**Enable this in fragment call not in activity*/
        fragmentTransaction.commit();
    }

    public void startFragment(Fragment fragment,Context context) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.addToBackStack(null);/**Enable this in fragment call not in activity*/
        fragmentTransaction.commit();
    }
}
