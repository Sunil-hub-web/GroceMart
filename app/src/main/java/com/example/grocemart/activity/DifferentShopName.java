package com.example.grocemart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.grocemart.R;
import com.example.grocemart.adapter.DifferentShopAadapter;
import com.example.grocemart.modelclass.DifferentShopDetails_ModelClass;
import com.example.grocemart.url.APPURLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DifferentShopName extends AppCompatActivity {

    RecyclerView recyclerShopDetails;
    GridLayoutManager shopdetails_layoutmanager;
    DifferentShopAadapter differentShopAadapter;
    ImageView img_NoProduct;

    ArrayList<DifferentShopDetails_ModelClass> shopDetails = new ArrayList<>();

    String subcategoryId,cityId;
    String shopId,shopName,shopBanner,shopLogo,shopAddress,City,State,Country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_different_shop_name);

        recyclerShopDetails = findViewById(R.id.recyclerShopDetails);
        img_NoProduct = findViewById(R.id.img_NoProduct);

        Intent intent = getIntent();

        subcategoryId = intent.getStringExtra("sucategoryId");
        cityId = intent.getStringExtra("cityId");

        getshopDetails(subcategoryId,cityId);

    }

    public void getshopDetails(String subcategoryId,String cityId){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.getShopDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");

                    if(message.equals("true")){

                        String shop = jsonObject.getString("All_shop");

                        JSONArray jsonArray_shop = new JSONArray(shop);

                        for (int i=0;i<jsonArray_shop.length();i++){

                            JSONObject jsonObject_shop = jsonArray_shop.getJSONObject(i);


                            shopId = jsonObject_shop.getString("shop_id");
                            shopName = jsonObject_shop.getString("shop_name");
                            shopBanner = jsonObject_shop.getString("shop_banner");
                            shopLogo = jsonObject_shop.getString("shop_logo");
                            shopAddress = jsonObject_shop.getString("shop_address");
                            City = jsonObject_shop.getString("city");
                            State = jsonObject_shop.getString("state");
                            Country = jsonObject_shop.getString("country");

                            DifferentShopDetails_ModelClass differentShopDetails_modelClass = new DifferentShopDetails_ModelClass(
                                    shopId,shopName,shopBanner,shopLogo,shopAddress,City,State,Country
                            );

                            shopDetails.add(differentShopDetails_modelClass);
                        }

                        if(shopDetails.size() == 0){

                            img_NoProduct.setVisibility(View.VISIBLE);

                        }else{

                            shopdetails_layoutmanager = new GridLayoutManager(DifferentShopName.this,1,GridLayoutManager.VERTICAL,false);
                            differentShopAadapter = new DifferentShopAadapter(DifferentShopName.this,shopDetails);
                            recyclerShopDetails.setLayoutManager(shopdetails_layoutmanager);
                            recyclerShopDetails.setHasFixedSize(true);
                            recyclerShopDetails.setAdapter(differentShopAadapter);

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                params.put("subcategory_id",subcategoryId);
                params.put("city_id",cityId);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(DifferentShopName.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }
}