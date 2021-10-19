package com.example.grocemart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CheckoutPage extends AppCompatActivity {

    Button btn_Address;
    TextView text_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_page);

        btn_Address = findViewById(R.id.addaddress);
        text_price = findViewById(R.id.price1);


        Intent intent = getIntent();
        String value = intent.getStringExtra("totalPrice");
        text_price.setText(value);


        btn_Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addAddress();

            }
        });
    }

    public void addAddress(){

        //Show Your Another AlertDialog
        final Dialog dialog = new Dialog(CheckoutPage.this);
        dialog.setContentView(R.layout.address_details);
        dialog.setCancelable(false);
        Button btn_SaveAddress = dialog.findViewById(R.id.saveaddress);

        btn_SaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }
}