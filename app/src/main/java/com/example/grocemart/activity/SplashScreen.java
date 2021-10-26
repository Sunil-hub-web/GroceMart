package com.example.grocemart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Selection;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grocemart.R;

public class SplashScreen extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

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