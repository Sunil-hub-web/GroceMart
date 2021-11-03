package com.example.grocemart.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.grocemart.R;
import com.example.grocemart.adapter.SerachAdapter;
import com.example.grocemart.modelclass.Serach_ModelClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SerachPage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    SerachAdapter serachAdapter;
    EditText edit_Serach;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    ArrayList<Serach_ModelClass> serachProduct = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach_page);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        edit_Serach = findViewById(R.id.serach);
        recyclerView = findViewById(R.id.serachRecycler);
        linearLayoutManager = new LinearLayoutManager(SerachPage.this,LinearLayoutManager.VERTICAL,false);

        bottomNavigationView.setSelectedItemId(R.id.search);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                switch(item.getItemId()){

                    case R.id.dashboard :
                        startActivity(new Intent(getApplicationContext(),UserDashboard.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.search:
                        return true;

                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(),CartPage.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

        serachProduct.add(new Serach_ModelClass(R.drawable.royal,"BB Royal Organic-Toor Dal","150","Narendra Shop"));
        serachProduct.add(new Serach_ModelClass(R.drawable.biriyani,"Aachi Biriyani Masala","150","Bharathi Super Store"));
        serachProduct.add(new Serach_ModelClass(R.drawable.meat,"Aachi Biriyani Masala","150","Narendra Shop"));
        serachProduct.add(new Serach_ModelClass(R.drawable.biriyani,"BB Royal Organic-Toor Dal","150","Bharathi Super Store"));


        serachAdapter = new SerachAdapter(SerachPage.this,serachProduct);
        recyclerView.setLayoutManager (linearLayoutManager);
        recyclerView.setHasFixedSize (true);
        recyclerView.setAdapter (serachAdapter);

        Log.d("item",serachProduct.toString());

        edit_Serach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                serachAdapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(SerachPage.this,MainActivity.class);
        startActivity(intent);
    }
}