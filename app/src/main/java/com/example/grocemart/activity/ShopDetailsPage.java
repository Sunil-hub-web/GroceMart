package com.example.grocemart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.example.grocemart.adapter.CategoryNameAdapter;
import com.example.grocemart.modelclass.AllShopDetails_ModelClass;
import com.example.grocemart.modelclass.CategoryName_ModelClass;
import com.example.grocemart.url.APPURLS;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopDetailsPage extends AppCompatActivity {

   RecyclerView recycler_CategoryName;
   GridLayoutManager gridLayoutManager;
   CategoryNameAdapter categoryNameAdapter;
   TextView shopName,ShopAddress;

   ArrayList<AllShopDetails_ModelClass> allShop;
   ArrayList<CategoryName_ModelClass> categoryName;
   private static final String TAG = "ShopDetailsPage";
   String cityId,shop_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details_page);

        recycler_CategoryName = findViewById(R.id.recycler_CategoryName);
        shopName = findViewById(R.id.shopName);
        ShopAddress = findViewById(R.id.ShopAddress);

        Intent intent = getIntent();

        cityId = intent.getStringExtra("cityId");
        shop_Id = intent.getStringExtra("shopId");

        getShopDetails(cityId,shop_Id);

    }

    public void getShopDetails(String city_Id,String shop_Id){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, APPURLS.getSingleShopDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");

                    if(message.equals("true")){

                        allShop = new ArrayList<>();

                        String shopId = jsonObject.getString("shop_id");
                        String shop_name = jsonObject.getString("shop_name");
                        String shop_img = jsonObject.getString("shop_img");
                        String shop_address = jsonObject.getString("shop_address");
                        String All_singleshop = jsonObject.getString("All_singleshop_product");

                        shopName.setText(shop_name);
                        ShopAddress.setText(shop_address);

                        if(shop_address.equals("")){

                            ShopAddress.setVisibility(View.GONE);
                        }

                        categoryName = new ArrayList<>();

                        JSONArray jsonArray_AllShop = new JSONArray(All_singleshop);

                        for(int i=0;i<jsonArray_AllShop.length();i++){

                            JSONObject jsonObject_AllShop = jsonArray_AllShop.getJSONObject(i);

                            String category_Name = jsonObject_AllShop.getString("Category_name");

                            CategoryName_ModelClass categoryName_modelClass = new CategoryName_ModelClass(category_Name);

                            categoryName.add(categoryName_modelClass);
                        }

                        AllShopDetails_ModelClass allShopDetails_modelClass = new AllShopDetails_ModelClass(
                                shopId,shop_name,shop_img,shop_address,categoryName
                        );

                        allShop.add(allShopDetails_modelClass);

                        gridLayoutManager = new GridLayoutManager(ShopDetailsPage.this,2,GridLayoutManager.VERTICAL,false);
                        categoryNameAdapter = new CategoryNameAdapter(ShopDetailsPage.this,categoryName);
                        recycler_CategoryName.setLayoutManager(gridLayoutManager);
                        recycler_CategoryName.setHasFixedSize(true);
                        recycler_CategoryName.setAdapter(categoryNameAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> param = new HashMap<>();

                param.put("city_id",city_Id);
                param.put("shop_id",shop_Id);

                return param;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ShopDetailsPage.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }
}