package com.example.grocemart.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.grocemart.R;

public class MyOrderDetails extends AppCompatActivity {

    Button btn_Shooping;
    ImageView image_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_details);

        btn_Shooping = findViewById(R.id.continueShoping);
        image_back = findViewById(R.id.back);

        btn_Shooping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyOrderDetails.this,MainActivity.class);
                startActivity(intent);
            }
        });

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyOrderDetails.this,UserDashboard.class);
                startActivity(intent);
            }
        });
    }
}