package com.example.grocemart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyOrderDetails extends AppCompatActivity {

    Button btn_Shooping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_details);

        btn_Shooping = findViewById(R.id.continueShoping);

        btn_Shooping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyOrderDetails.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}