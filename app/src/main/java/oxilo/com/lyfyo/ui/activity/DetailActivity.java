package oxilo.com.lyfyo.ui.activity;

import android.Manifest;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.rxpermission.RxPermissions;
import oxilo.com.lyfyo.ui.fragments.DetailFragment;
import oxilo.com.lyfyo.ui.fragments.HomeFragment;
import oxilo.com.lyfyo.ui.fragments.ServiceFragments;

public class DetailActivity extends AppCompatActivity implements DetailFragment.OnFragmentInteractionListener,ServiceFragments.OnFragmentInteractionListener{

    RxPermissions rxPermissions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content, DetailFragment.newInstance("",""));
//            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
