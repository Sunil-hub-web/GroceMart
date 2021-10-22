package com.example.grocemart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AddressDetails extends AppCompatActivity {

    ImageView image_back;
    Button btn_AddAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_details);

        image_back = findViewById(R.id.back);
        btn_AddAddress = findViewById(R.id.addaddress);

        btn_AddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addAddress();
            }
        });

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddressDetails.this,UserDashboard.class);
                startActivity(intent);
            }
        });

    }

    public void addAddress(){

        //Show Your Another AlertDialog
        final Dialog dialog = new Dialog(AddressDetails.this);
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
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.drawable.dialogback);
    }


    public  void deleteAddress(String AddressId){

    }
}