package com.mattricks.deliverit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.mattricks.deliverit.utilities.SharedPreference;

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 3000;
    SharedPreference sharedPreference;


    @Override
    public void onAttachedToWindow() {
        // TODO Auto-generated method stub
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreference = new SharedPreference();
        final boolean isLogin = sharedPreference.isLogin(SplashActivity.this);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (isLogin) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();

                } else {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);

    }

}
