package com.example.grocemart.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.grocemart.R;
import com.example.grocemart.adapter.ProductShopAdapter;
import com.example.grocemart.modelclass.ProductShop_ModelClass;
import com.example.grocemart.modelclass.Variation_ModelClass;
import com.example.grocemart.url.APPURLS;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductShopDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ProductShopAdapter productShopAdapter;
    ImageView image_Product;
    ArrayList<ProductShop_ModelClass> productShop = new ArrayList<>();
    ArrayList<ProductShop_ModelClass> itemArraylist;
    List<Variation_ModelClass> variations;


    String productId, cityId;
    public static TextView itemcounter;
    private static final String TAG = "ProductShopDetails" ;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_shop_details);


        Intent intent = getIntent();
        productId = intent.getStringExtra("productId");
        cityId = intent.getStringExtra("cityId");

        recyclerView = findViewById(R.id.shopDetailsRecycler);
        image_Product = findViewById(R.id.image_Product);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        getProductDetails(productId, cityId);

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
                        startActivity(new Intent(getApplicationContext(), CartPage.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), SerachPage.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });

    }

    public void getProductDetails(String productId, String cityId) {

        ProgressDialog progressDialog = new ProgressDialog(ProductShopDetails.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.getProductDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");

                    if (message.equals("true")) {

                        itemArraylist = new ArrayList<ProductShop_ModelClass>();

                        String allShop = jsonObject.getString("All_shop");

                        JSONArray jsonArray_shop = new JSONArray(allShop);

                        for (int i = 0; i < jsonArray_shop.length(); i++) {

                            JSONObject jsonObject_Shop = jsonArray_shop.getJSONObject(i);

                            String productid = jsonObject_Shop.getString("product_id");
                            String productname = jsonObject_Shop.getString("product_name");
                            String productdesc = jsonObject_Shop.getString("product_description");
                            String productimage = jsonObject_Shop.getString("product_img");
                            String shopId = jsonObject_Shop.getString("shop_id");
                            String shopName = jsonObject_Shop.getString("shop_name");
                            String shopAddress = jsonObject_Shop.getString("shop_address");

                            variations = new ArrayList<Variation_ModelClass>();

                            JSONArray jsonArray_variation = jsonObject_Shop.getJSONArray("All_variation");
                            if (jsonArray_variation.length() == 0) {

                            } else {

                                for (int j = 0; j < jsonArray_variation.length(); j++) {

                                    JSONObject jsonObject_variation = jsonArray_variation.getJSONObject(j);

                                    String variationId = jsonObject_variation.getString("variation_id");
                                    String unit = jsonObject_variation.getString("unit");
                                    String mrpPrice = jsonObject_variation.getString("mrp_price");
                                    String salesPrice = jsonObject_variation.getString("sale_price");
                                    String discount = jsonObject_variation.getString("discount");

                                    Variation_ModelClass variation_modelClass = new Variation_ModelClass(
                                            variationId, unit, mrpPrice, salesPrice, discount);

                                    variations.add(variation_modelClass);

                                }

                                Log.d("variations",variations.size()+"");

                            }

                            if (!productid.equalsIgnoreCase("null")) {

                                ProductShop_ModelClass productShop_modelClass = new ProductShop_ModelClass(
                                        productid, productname, productdesc, productimage, shopId, shopName,
                                        shopAddress, "", "", "", "","" ,variations,cityId
                                );

                                itemArraylist.add(productShop_modelClass);
                            }

                            Log.d("variationsChck",itemArraylist.size()+"");
                        }

                        Log.d("itemArraylist",itemArraylist.toString());


                        if (itemArraylist.size() != 0) {

                            linearLayoutManager = new LinearLayoutManager(ProductShopDetails.this, LinearLayoutManager.VERTICAL, false);
                            productShopAdapter = new ProductShopAdapter(ProductShopDetails.this, itemArraylist);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(productShopAdapter);

                        } else {

                            image_Product.setVisibility(View.VISIBLE);
                        }

                    }
                } catch (
                        JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();

                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("product_id", productId);
                params.put("city_id", cityId);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ProductShopDetails.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }
}