package com.example.grocemart.activity;

import android.app.UiModeManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Selection;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.grocemart.R;

public class SplashScreen extends AppCompatActivity {

    Handler handler;

    private UiModeManager uiModeManager;



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {

            setTheme(R.style.DarkTheme);

        } else {

            setTheme(R.style.LightTheme);
        }

        setTheme(R.style.LightTheme);
        setContentView(R.layout.activity_splash_screen);

        //uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);

        //uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        } else {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        handler = new Handler();

        handler.postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(SplashScreen.this, SelectLocation.class);
                startActivity(i);
                finish();
            }
        }, 5000);
    }

}