package com.example.grocemart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.example.grocemart.adapter.ProductShopAdapter;
import com.example.grocemart.modelclass.ProductShop_ModelClass;
import com.example.grocemart.modelclass.Variation_ModelClass;
import com.example.grocemart.url.APPURLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductShopDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ProductShopAdapter productShopAdapter;
    ImageView image_Product;
    ArrayList<ProductShop_ModelClass> productShop = new ArrayList<>();
    ArrayList<ProductShop_ModelClass> itemArraylist = new ArrayList<ProductShop_ModelClass>();
    ArrayList<Variation_ModelClass> variations = new ArrayList<Variation_ModelClass>();

    String productId, cityId;
    public static TextView itemcounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_shop_details);


        Intent intent = getIntent();
        productId = intent.getStringExtra("productId");
        cityId = intent.getStringExtra("cityId");

        recyclerView = findViewById(R.id.shopDetailsRecycler);
        image_Product = findViewById(R.id.image_Product);

        getProductDetails(productId, cityId);

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

                        String allShop = jsonObject.getString("All_shop");

                        JSONArray jsonArray_shop = new JSONArray(allShop);

                        for (int i = 0; i < jsonArray_shop.length(); i++) {

                            JSONObject jsonObject_Shop = jsonArray_shop.getJSONObject(i);

                            String productid = (jsonObject_Shop.getString("product_id"));
                            String productname = (jsonObject_Shop.getString("product_name"));
                            String productdesc = (jsonObject_Shop.getString("product_description"));
                            String productimage = (jsonObject_Shop.getString("product_img"));
                            String shopId = (jsonObject_Shop.getString("shop_id"));
                            String shopName = (jsonObject_Shop.getString("shop_name"));
                            String shopAddress = (jsonObject_Shop.getString("shop_address"));
                            String allVariation = (jsonObject_Shop.getString("All_variation"));

                            JSONArray jsonArray_variation = new JSONArray(allVariation);

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
                                            variationId, unit, mrpPrice, salesPrice, discount
                                    );

                                    variations.add(variation_modelClass);

                                }

                            }
                            if (!productid.equalsIgnoreCase("null")) {

                                ProductShop_ModelClass productShop_modelClass = new ProductShop_ModelClass(
                                        productId, productname, productdesc, productimage,
                                        shopId, shopName, shopAddress, "", "", "", "","" ,variations
                                );

                                itemArraylist.add(productShop_modelClass);
                            }
                        }


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