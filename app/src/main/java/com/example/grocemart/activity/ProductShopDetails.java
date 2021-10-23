package com.example.grocemart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.grocemart.R;
import com.example.grocemart.adapter.ProductShopAdapter;
import com.example.grocemart.modelclass.ProductShop_ModelClass;

import java.util.ArrayList;

public class ProductShopDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ProductShopAdapter productShopAdapter;
    ArrayList<ProductShop_ModelClass> productShop = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_shop_details);


        Intent intent = getIntent();
        String Message = intent.getStringExtra("message");

        recyclerView = findViewById(R.id.shopDetailsRecycler);
        linearLayoutManager = new LinearLayoutManager(ProductShopDetails.this,LinearLayoutManager.VERTICAL,false);

       /* productShop.add(new ProductShop_ModelClass(R.drawable.royal,
                "BB Royal Organic-Toor Dal","Narendra Shop","16, Saheed Nagar, Bhubaneswar, Odisha 751007, India"));

        productShopAdapter = new ProductShopAdapter(ProductShopDetails.this,productShop);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(productShopAdapter);*/

        if(Message.equals("Aachi Biriyani Masala")){

            productShop.add(new ProductShop_ModelClass(R.drawable.biriyani,
                    "Aachi Biriyani Masala","Narendra Shop","16, Saheed Nagar, Bhubaneswar, Odisha 751007, India"));
            productShop.add(new ProductShop_ModelClass(R.drawable.biriyani,
                    "Aachi Biriyani Masala","Bharathi Super Store","I Agraharam, Salem, Tamil Nadu 636001, India"));

            productShopAdapter = new ProductShopAdapter(ProductShopDetails.this,productShop);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(productShopAdapter);


        }else if (Message.equals("chicken")){

            productShop.add(new ProductShop_ModelClass(R.drawable.chicken,
                    "Hyderabadi Chicken Biriyani","Narendra Shop","16, Saheed Nagar, Bhubaneswar, Odisha 751007, India"));
            productShopAdapter = new ProductShopAdapter(ProductShopDetails.this,productShop);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(productShopAdapter);

        }else {

            productShop.add(new ProductShop_ModelClass(R.drawable.royal,
                    "BB Royal Organic-Toor Dal","Narendra Shop","16, Saheed Nagar, Bhubaneswar, Odisha 751007, India"));
            productShopAdapter = new ProductShopAdapter(ProductShopDetails.this,productShop);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(productShopAdapter);
        }

    }
}