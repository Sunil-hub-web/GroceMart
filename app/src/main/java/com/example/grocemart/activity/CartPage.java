package com.example.grocemart.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocemart.R;
import com.example.grocemart.adapter.ProductAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartPage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ProductAdapter productAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    int Images[]={R.drawable.royal,R.drawable.biriyani,R.drawable.meat,R.drawable.biriyani};
    String [] name ={"BB Royal Organic-Toor Dal","Aachi Biriyani Masala","Aachi Biriyani Masala","Aachi Biriyani Masala"};
    String [] price = {"150","588","588","500"};
    Button btn_CheckOut;
    TextView text_price1;
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        recyclerView = findViewById(R.id.recycler);
        btn_CheckOut = findViewById(R.id.checkOut);
        text_price1 = findViewById(R.id.price1);
        linearLayoutManager = new LinearLayoutManager(CartPage.this,LinearLayoutManager.VERTICAL,false);

        bottomNavigationView.setSelectedItemId(R.id.cart);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                switch(item.getItemId()){

                    case R.id.dashboard :
                        startActivity(new Intent(getApplicationContext(),UserDashboard.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home :
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.cart:
                        return true;

                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(),SerachPage.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

        productAdapter = new ProductAdapter (CartPage.this,Images,name,price);
        recyclerView.setLayoutManager (linearLayoutManager);
        recyclerView.setHasFixedSize (true);
        recyclerView.setAdapter (productAdapter);

        btn_CheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                value = productAdapter.getvalue();
                Toast.makeText(CartPage.this, ""+value, Toast.LENGTH_SHORT).show();
                text_price1.setText(String.valueOf("₹ "+value));

               /* Intent intent = new Intent(CartPage.this,CheckoutPage.class);
                intent.putExtra("totalPrice",value);
                startActivity(intent);*/

            }
        });

    }

}