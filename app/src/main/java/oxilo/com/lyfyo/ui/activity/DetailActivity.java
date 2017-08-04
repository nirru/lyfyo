package oxilo.com.lyfyo.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import oxilo.com.lyfyo.ApplicationController;
import oxilo.com.lyfyo.LyfoPrefs;
import oxilo.com.lyfyo.R;
import oxilo.com.lyfyo.ui.fragments.DetailFragment;
import oxilo.com.lyfyo.ui.fragments.ServiceFragments;
import oxilo.com.lyfyo.ui.modal.FilterDatum;
import oxilo.com.lyfyo.ui.modal.UserDetail;

import static oxilo.com.lyfyo.AppConstant.USER_DETAIL;

public class DetailActivity extends AppCompatActivity implements DetailFragment.OnFragmentInteractionListener, ServiceFragments.OnFragmentInteractionListener {

    FilterDatum filterDatum;
    @BindView(R.id.continue_rl)
    RelativeLayout continueRl;
    @BindView(R.id.price)
    TextView price;

    double total_price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        if (getIntent() != null)
            filterDatum = getIntent().getParcelableExtra("list");
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content, DetailFragment.newInstance(filterDatum, ""));
//            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @OnClick(R.id.continue_rl)
    public void onViewClicked() {
        LyfoPrefs lyfoPrefs = new LyfoPrefs();
        lyfoPrefs.getLyfoPrefs(DetailActivity.this);
        UserDetail userDetail = ApplicationController.getInstance().getAppPrefs().getObject(USER_DETAIL, UserDetail.class);

        if (price.getText().toString().equals("0.0")){
            Toast.makeText(DetailActivity.this,"please add atleast one service",Toast.LENGTH_SHORT).show();
        }else{
            if (userDetail==null) {
                showLoginAlert(DetailActivity.this);
            } else {
                //go directly to date time page
                Intent i = new Intent(DetailActivity.this,CalenderActivity.class);
                startActivity(i);
            }
        }

    }

    public void showLoginAlert(final Context mContext) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);

        // Setting Dialog Title
        builder.setTitle("Login");
        builder.setMessage("Its seems that you are not login .Please login first");

        // On pressing Settings button
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(DetailActivity.this, LoginActivity.class);
                mContext.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        // display dialog
        dialog.show();
    }

    public TextView getPriceView(){
        return price;
    }

    public double getTotal (){
        return total_price;
    }

    public void setTotal(double total){
        total_price = total;
    }
}
