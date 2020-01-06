package com.vn.myhome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.vn.myhome.MainActivity;
import com.vn.myhome.R;
import com.vn.myhome.activity.login.LoginActivity;
import com.vn.myhome.base.BaseActivity;
import com.vn.myhome.config.Constants;
import com.vn.myhome.untils.SharedPrefs;


public class SplashScreenActivity extends BaseActivity {
    private static final String TAG = "SplashScreen";

    ImageView img_splash;
    // public static Storage storage; // this Preference comes for free from the library
    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    Intent mainIntent = new Intent();
    Intent mainIntent_welcom = new Intent();
    String id;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        boolean isLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_IS_LOGIN, Boolean.class);
/*        img_splash = (ImageView) findViewById(R.id.img_logo);
        Glide.with(this).load(R.drawable.img_splash).into(img_splash);*/
        if (isLogin) {
            mainIntent.setClass(SplashScreenActivity.this, MainActivity.class);
        } else {
            mainIntent.setClass(SplashScreenActivity.this, LoginActivity.class);
        }
        start_activity();
    }

    private String sTokenKey;


    private void start_activity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_flash;
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
}