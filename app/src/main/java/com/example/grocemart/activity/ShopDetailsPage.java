package com.example.grocemart.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.grocemart.R;

public class ShopDetailsPage extends AppCompatActivity {

    Button btn_RESTAURANT;
    TextView text_name,text_address,text_vag;
    RelativeLayout rel_Nonvag,rel_first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details_page);

        btn_RESTAURANT = findViewById(R.id.RESTAURANT);
        text_name = findViewById(R.id.text);
        text_address = findViewById(R.id.address);
        text_vag = findViewById(R.id.itemname4);
        rel_first = findViewById(R.id.first);
        rel_Nonvag = findViewById(R.id.NonVeg);

        Intent intent = getIntent();

        String message = intent.getStringExtra("message");

        if(message != null){

            btn_RESTAURANT.setVisibility(View.GONE);
            text_name.setText("Bharathi Super Store");
            text_address.setText("I Agraharam, Salem, Tamil Nadu 636001, India");
            rel_Nonvag.setVisibility(View.GONE);
            rel_first.setVisibility(View.GONE);
            text_vag.setVisibility(View.GONE);


        }
    }
}