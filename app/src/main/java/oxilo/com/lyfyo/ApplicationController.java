package oxilo.com.lyfyo;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;


/**
 * Created by nikk on 28/6/17.
 */

public class ApplicationController extends Application {

    private AppPrefs appPrefs;
    private static ApplicationController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        appPrefs = AppPrefs.getComplexPreferences(getBaseContext(), "lyfo_prefs", MODE_PRIVATE);
//        refWatcher = LeakCanary.install(this);
    }

    public static synchronized ApplicationController getInstance() {
        return mInstance;
    }
//    public static RefWatcher getRefWatcher() {
//        return refWatcher;
//    }

    public void showSettingsAlert(final Context mContext){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext,R.style.MyDialogTheme);

        // Setting Dialog Title
        builder.setTitle(getString(R.string.setting_dialog_title));
        builder.setMessage(getString(R.string.setting_dialog_message));

        // On pressing Settings button
        builder.setPositiveButton(mContext.getString(R.string.agree), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
        builder.setNegativeButton(mContext.getString(R.string.disagree), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        // display dialog
        dialog.show();
    }

    public AppPrefs getAppPrefs() {
        if(appPrefs != null) {
            return appPrefs;
        }
        return null;
    }
}
