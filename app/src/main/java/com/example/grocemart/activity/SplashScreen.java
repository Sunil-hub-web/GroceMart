package com.example.grocemart.activity;

import android.app.UiModeManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Selection;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.grocemart.R;

public class SplashScreen extends AppCompatActivity {

    Handler handler;

    private UiModeManager uiModeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            setTheme(R.style.GroceMart);
        } else {
            setTheme(R.style.GroceMart);
        }

        setContentView(R.layout.activity_splash_screen);

        uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);

        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);




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

   /* public void NightModeON(View view){
        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
    }

    public void NightModeOFF(View view){
        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
    }*/
}