package com.example.grocemart.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.grocemart.R;
import com.example.grocemart.SharedPrefManager;
import com.example.grocemart.adapter.ProductAdapter;
import com.example.grocemart.modelclass.CartItem;
import com.example.grocemart.url.APPURLS;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartPage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ProductAdapter productAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Button btn_CheckOut;
    TextView subTotalPrice;
    int value;
    ArrayList<CartItem> allCartItem = new ArrayList<CartItem>();
    ImageView image_NoResult;
    public String productId,variationId,shopId,productImage, productName,shopName,unit,
            salePrice,discount,quantity,userId;

    double totalprice,sales_Price,quanTity,totalAmount = 0;

    RelativeLayout rel_MoneyTotal,rel_Total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);

        userId = SharedPrefManager.getInstance(CartPage.this).getUser().getId();

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        recyclerView = findViewById(R.id.recycler);
        btn_CheckOut = findViewById(R.id.checkOut);
        subTotalPrice = findViewById(R.id.subTotalPrice);
        image_NoResult = findViewById(R.id.image_NoResult);
        rel_MoneyTotal = findViewById(R.id.rel_MoneyTotal);
        rel_Total = findViewById(R.id.rel_Total);

        bottomNavigationView.setSelectedItemId(R.id.cart);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.cart:
                        return true;

                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), SerachPage.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });

            btn_CheckOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(CartPage.this, CheckoutPage.class);
                    startActivity(intent);

                }
            });

            getCartItem(userId);

        }

        public void getCartItem(String userId){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.getCartItem, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");

                    if(message.equals("true")){

                        String allcart = jsonObject.getString("All_Cart");

                        JSONArray jsonArray_AllCart = new JSONArray(allcart);

                        for(int i=0;i<jsonArray_AllCart.length();i++){


                            JSONObject jsonObject_allCart = jsonArray_AllCart.getJSONObject(i);

                            productId = jsonObject_allCart.getString("product_id");
                            variationId = jsonObject_allCart.getString("variation_id");
                            shopId = jsonObject_allCart.getString("shop_id");
                            productImage = jsonObject_allCart.getString("product_img");
                            productName = jsonObject_allCart.getString("product_name");
                            shopName = jsonObject_allCart.getString("shop_name");
                            unit = jsonObject_allCart.getString("unit");
                            salePrice = jsonObject_allCart.getString("sale_price");
                            discount = jsonObject_allCart.getString("discount");
                            quantity = jsonObject_allCart.getString("quantity");

                            CartItem cartItem = new CartItem(
                                    productId,variationId,shopId,productImage,productName,shopName,
                                    unit,salePrice,discount,quantity,salePrice
                            );

                            sales_Price = Double.valueOf(salePrice);

                            quanTity = Double.valueOf(quantity);

                            totalprice = sales_Price * quanTity;


                            totalAmount = totalAmount + totalAmount;

                            allCartItem.add(cartItem);



                        }

                        String total_price = String.valueOf(totalAmount);

                        subTotalPrice.setText(total_price);

                        if(allCartItem.size() == 0){

                            rel_MoneyTotal.setVisibility(View.GONE);
                            rel_Total.setVisibility(View.GONE);
                            image_NoResult.setVisibility(View.VISIBLE);

                        }else{

                            rel_MoneyTotal.setVisibility(View.VISIBLE);
                            rel_Total.setVisibility(View.VISIBLE);
                            image_NoResult.setVisibility(View.GONE);

                            linearLayoutManager = new LinearLayoutManager(CartPage.this, LinearLayoutManager.VERTICAL, false);
                            productAdapter = new ProductAdapter(CartPage.this, allCartItem);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(productAdapter);
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace ();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("user_id",userId);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(CartPage.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(CartPage.this,MainActivity.class);
        startActivity(intent);
    }
}