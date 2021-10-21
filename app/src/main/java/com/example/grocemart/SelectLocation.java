package com.example.grocemart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SelectLocation extends AppCompatActivity {

    String[] reason = { "--Select Your City--", "Bhubanesware", "Cuttack"};
    String[] sqft = { "--Select Your Pincode--","750017", "753009"};

    Spinner spiner_City,spiner_pincode;
    Button btn_submit;
    String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        spiner_City = findViewById(R.id.spinner_city);
        spiner_pincode = findViewById(R.id.spinner_pincode);
        btn_submit = findViewById(R.id.Next);

        ArrayAdapter reson_for_city = new ArrayAdapter(this,android.R.layout.simple_spinner_item,reason);
        // Drop down layout style - list view with radio button
        reson_for_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner_City.setAdapter(reson_for_city);
        spiner_City.setSelection(-1,true);

        ArrayAdapter reson_for_pincode = new ArrayAdapter(this,android.R.layout.simple_spinner_item,sqft);
        reson_for_pincode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner_pincode.setAdapter(reson_for_pincode);
        spiner_pincode.setSelection(-1,true);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SelectLocation.this,MainActivity.class);
                intent.putExtra("item",item);
                startActivity(intent);

            }
        });

        spiner_City.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                item = reason[position];
                Toast.makeText(SelectLocation.this, item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}